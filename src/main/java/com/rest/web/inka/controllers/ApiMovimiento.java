package com.rest.web.inka.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rest.web.inka.models.CategoriaProductos;
import com.rest.web.inka.models.Movimiento;
import com.rest.web.inka.models.MovimientoDto;
import com.rest.web.inka.models.Producto;
import com.rest.web.inka.models.ProductoDto;
import com.rest.web.inka.models.Provedor;
import com.rest.web.inka.models.TipoMovimiento;
import com.rest.web.inka.models.TipoProducto;
import com.rest.web.inka.service.ICategoriaProductoService;
import com.rest.web.inka.service.IMovimientoService;
import com.rest.web.inka.service.IProductoService;
import com.rest.web.inka.service.IProvedorService;
import com.rest.web.inka.service.ITipoMovimientoService;
import com.rest.web.inka.service.ITipoProductoService;
import com.rest.web.inka.utilidades.PaginationMod;
import com.rest.web.inka.utilidades.Utilidades;

@RestController
@RequestMapping("/api/movimientos")
public class ApiMovimiento {

	@Autowired
	private IMovimientoService movimientoService;
	
	@Autowired
	private IProvedorService provedorService;
	
	@Autowired
	private ITipoMovimientoService tipoMovimiento;

	@GetMapping("/index/{nombre}")
	public String index(@PathVariable String nombre) {
		return "METODO GET: " + nombre;
	}

	@GetMapping("/index-response")
	public ResponseEntity<String> indexResponse() {
		return ResponseEntity.ok("METODO GET");
	}
	
	@GetMapping("/listar")
	public Object listar(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "") String nombre) {
	
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		
		PaginationMod<MovimientoDto> enviar = movimientoService.listarMovimientoDtoPaginado(nombre,PageRequest.of(page, 5, Sort.by("id").descending()));
		
//		List<CategoriaProductos> categoria = categoriaServiceProduto.listarCategoriaProducto();
		
		map.put("movimientos", enviar);
//	    map.put("categorias", categoria);
		
	    return map;
	}
	
	@GetMapping("/movimiento/{id}")
	public Movimiento listarId(@PathVariable Integer id) {
		return movimientoService.buscarIdMovimiento(id);
	}

	@PostMapping("/movimiento")
	public ResponseEntity<Object> guardarMovimiento(@RequestBody Movimiento movimiento){

			TipoMovimiento tipMov = tipoMovimiento.buscarIdTipoMovimiento(movimiento.getTipo().getId());
			
			if(tipMov == null) {
				return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL TIPO");
			}
			
			
			Provedor prov = provedorService.buscarIdProvedor(movimiento.getProvedor().getId());
			
			if(prov == null) {
				return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL PROVEDOR");
			}
			

			movimientoService.guardar(movimiento);
			
			return Utilidades.generateResponseTrue(HttpStatus.CREATED, "PRODUCTO CREADO CORRECTAMENTE");
		
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
