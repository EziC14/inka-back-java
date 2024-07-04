package com.rest.web.inka.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.web.inka.models.Movimiento;
import com.rest.web.inka.models.MovimientoDto;
import com.rest.web.inka.models.Producto;
import com.rest.web.inka.models.Provedor;
import com.rest.web.inka.models.TipoMovimiento;
import com.rest.web.inka.service.IMovimientoService;
import com.rest.web.inka.service.IProductoService;
import com.rest.web.inka.service.IProvedorService;
import com.rest.web.inka.service.ITipoMovimientoService;
import com.rest.web.inka.service.PdfService;
import com.rest.web.inka.utilidades.PaginationMod;
import com.rest.web.inka.utilidades.Utilidades;
import org.springframework.http.MediaType;


@RestController
@RequestMapping("/api/movimientos")
public class ApiMovimiento {

	@Autowired
	private IMovimientoService movimientoService;
	
	@Autowired
	private IProvedorService provedorService;
	
	@Autowired
	private ITipoMovimientoService tipoMovimiento;
	
	@Autowired
	private IProductoService productoService;

	@Autowired
    private PdfService pdfService;

    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> movimientosReport() throws Exception {
        File pdfFile = pdfService.generateMovimientoPdf();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=report_movimiento.pdf");

        InputStreamResource resource = new InputStreamResource(new FileInputStream(pdfFile));

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
	
	@GetMapping("/index/{nombre}")
	public String index(@PathVariable String nombre) {
		return "METODO GET: " + nombre;
	}

	@GetMapping("/index-response")
	public ResponseEntity<String> indexResponse() {
		return ResponseEntity.ok("METODO GET");
	}
	
	@GetMapping("/listar")
	public Object listar(@RequestParam(required = false, defaultValue = "0") Integer page,
	                     @RequestParam(required = false, defaultValue = "") String nombre,
	                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
	                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo) {
	    
	    HashMap<Object, Object> map = new HashMap<Object, Object>();

	    // Verificar si los parámetros de paginación son válidos
	    if (page < 0) {
	        return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "El número de página debe ser mayor o igual a 0");
	    }

	    // Verificar si el nombre es nulo
	    if (nombre == null) {
	        nombre = "";
	    }

	    // Verificar si las fechas son válidas
	    if (dateFrom != null && dateTo != null && dateFrom.isAfter(dateTo)) {
	        return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "La fecha 'dateFrom' no puede ser posterior a la fecha 'dateTo'");
	    }

	    // Convertir LocalDate a Date
	    Date fromDate = (dateFrom != null) ? java.sql.Date.valueOf(dateFrom) : null;
	    Date toDate = (dateTo != null) ? java.sql.Date.valueOf(dateTo) : null;

	    PaginationMod<MovimientoDto> enviar = movimientoService.listarMovimientoDtoPaginado(nombre, fromDate, toDate, 
	                                                PageRequest.of(page, 9, Sort.by("id").descending()));

	    // Verificar si se obtuvo algún resultado
	    if (enviar == null || enviar.getContent().isEmpty()) {
	        return Utilidades.generateResponse(HttpStatus.NOT_FOUND, "No se encontraron movimientos");
	    }

	    map.put("movimientos", enviar);
	    return map;
	}
	
	@GetMapping("/dashboard")
    public String getDashboard() {
        List<Movimiento> movimientos = movimientoService.getListMovimiento();
        Map<String, Integer> proveedorCantidadMap = new HashMap<>();

        // Iterar sobre los movimientos
        for (Movimiento movimiento : movimientos) {
            if (movimiento.getProvedor() != null) {
                String nombreProvedor = movimiento.getProvedor().getNombre();
                String cantidadStr = movimiento.getCantidad();
                Integer cantidad = null;
                try {
                    cantidad = Integer.parseInt(cantidadStr);
                } catch (NumberFormatException e) {
                    System.out.println("Error al convertir cantidad a Integer para el movimiento con ID: " + movimiento.getId());
                    e.printStackTrace();
                    continue; // Continuar con el siguiente movimiento si hay error
                }

                // Sumar las cantidades si el proveedor ya existe en el mapa
                proveedorCantidadMap.merge(nombreProvedor, cantidad, Integer::sum);
            } else {
                // Manejar el caso donde provedor es null, si es necesario
                System.out.println("Proveedor es null para el movimiento con ID: " + movimiento.getId());
            }
        }

        // Convertir el mapa a una lista de mapas
        List<Map<String, Object>> data = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : proveedorCantidadMap.entrySet()) {
            Map<String, Object> entryMap = new HashMap<>();
            entryMap.put("nombre", entry.getKey());
            entryMap.put("cantidad", entry.getValue());
            data.add(entryMap);
        }

        // Convertir data a formato JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = "";
        try {
            jsonData = objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonData;
    }

	@GetMapping("/movimiento/{id}")
	public Movimiento listarId(@PathVariable Integer id) {
		return movimientoService.buscarIdMovimiento(id);
	}

	@PostMapping("/movimiento")
	public ResponseEntity<Object> guardarMovimiento(@RequestBody Movimiento movimiento) {
	    if (movimiento.getTipo() == null || movimiento.getTipo().getId() == null) {
	        return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "El tipo de movimiento es requerido");
	    }
	    
	    TipoMovimiento tipMov = tipoMovimiento.buscarIdTipoMovimiento(movimiento.getTipo().getId());
	    if (tipMov == null) {
	        return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL TIPO");
	    }
	    
		Producto producto = productoService.buscarIdProducto(movimiento.getProducto().getId());
	    if (producto == null) {
	        return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL PRODUCTO");
	    }
	    
	    if (tipMov.getId() == 1) {
	        // Si el tipo de movimiento es 1 (ingreso), validar proveedor y actualizar cantidad del producto
	        if (movimiento.getProvedor() == null || movimiento.getProvedor().getId() == null) {
	            return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "El proveedor es requerido");
	        }
	        
	        Provedor prov = provedorService.buscarIdProvedor(movimiento.getProvedor().getId());
	        if (prov == null) {
	            return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL PROVEEDOR");
	        }
	        
	        Integer newStock = producto.getStock() + Integer.parseInt(movimiento.getCantidad());
	        producto.setStock(newStock);
	    } else if (tipMov.getId() == 2) {
	        // Si el tipo de movimiento es 2 (salida), validar que no haya proveedor y que haya suficiente stock
	        if (movimiento.getProvedor() != null && movimiento.getProvedor().getId() != null) {
	            return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "No se debe especificar proveedor para este tipo de movimiento");
	        }
	        
	        if (producto.getStock() < Integer.parseInt(movimiento.getCantidad())) {
	            return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "No hay suficiente stock para realizar la salida");
	        }
	        
	        producto.setStock(producto.getStock() - Integer.parseInt(movimiento.getCantidad()));
	    }
	    
	    productoService.guardar(producto);
	    movimientoService.guardar(movimiento);
	    return Utilidades.generateResponseTrue(HttpStatus.CREATED, "MOVIMIENTO CREADO CORRECTAMENTE");
	}


	@PutMapping("/movimiento")
	public ResponseEntity<Object> actualizarMovimiento(@RequestBody Movimiento movimiento) {

		Movimiento mov = movimientoService.buscarIdMovimiento(movimiento.getId());
				
		if (mov == null) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "EL MOVIMIENTO NO SE ENCONTRÓ");
		}

		movimientoService.guardar(movimiento);

		return Utilidades.generateResponseTrue(HttpStatus.CREATED, "PRODUCTO ACTUALIZADO CORRECTAMENTE");
	}

	@PostMapping("/eliminar")
	public ResponseEntity<Object> eliminarMovimiento(@RequestBody Movimiento movimiento) {

		Movimiento mov = movimientoService.buscarIdMovimiento(movimiento.getId());

		if (mov == null) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "EL MOVIMIENTO NO SE ENCONTRÓ");
		}
		movimientoService.eliminar(movimiento.getId());

		return Utilidades.generateResponseTrue(HttpStatus.OK, "MOVIMIENTO SE ELIMINÓ CORRECTAMENTE");
	}
	
}
