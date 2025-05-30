package com.rest.web.inka.controllers;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
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
import com.rest.web.inka.models.Producto;
import com.rest.web.inka.models.ProductoDto;
import com.rest.web.inka.models.TipoProducto;
import com.rest.web.inka.service.ICategoriaProductoService;
import com.rest.web.inka.service.IProductoService;
import com.rest.web.inka.service.ITipoProductoService;
import com.rest.web.inka.service.PdfService;
import com.rest.web.inka.utilidades.PaginationMod;
import com.rest.web.inka.utilidades.Utilidades;


@RestController
@RequestMapping("/api/productos")

public class ApiProductos {

	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private ICategoriaProductoService categoriaServiceProduto;
	
	@Autowired
	private ICategoriaProductoService categoriaProducto;
	
	@Autowired
	private ITipoProductoService tipoProducto;

	@Autowired
    private PdfService pdfService;

    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> productosReport() throws Exception {
        File pdfFile = pdfService.generateProductoPdf();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=report_stock.pdf");

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
	
	@GetMapping("/listar/bajo-stock")
	public ResponseEntity<Object> listarBajoStock(
			@RequestParam(required = false, defaultValue = "0") Integer page, 
			@RequestParam(required = false, defaultValue = "10") Integer stockMinimo) {
		
		try {
			PaginationMod<ProductoDto> productos = productoService.listarProductosBajoStock(
					stockMinimo, 
					PageRequest.of(page, 10, Sort.by("stock").ascending()));
			
			HashMap<String, Object> response = new HashMap<>();
			response.put("productos", productos);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			HashMap<String, String> error = new HashMap<>();
			error.put("error", "Error al obtener productos con bajo stock: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
		}
	}
	
	@GetMapping("/listar")
	public Object listar(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "") String nombre) {
	
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		
		PaginationMod<ProductoDto> enviar = productoService.listarProductoDtoPaginado(nombre,PageRequest.of(page, 9, Sort.by("id").descending()));
		
		List<CategoriaProductos> categoria = categoriaServiceProduto.listarCategoriaProducto();
		
		map.put("productos", enviar);
	    map.put("categorias", categoria);
		
	    return map;
	}

	@GetMapping("/listar/name")
	public Object listarNombre(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "") String nombre) {
		HashMap<Object, Object> map = new HashMap<>();
		
		PaginationMod<ProductoDto> enviar = productoService.buscarProductoPorNombrePaginado(nombre,
				PageRequest.of(page, 9, Sort.by("id").descending()));
		
		List<CategoriaProductos> categoria = categoriaServiceProduto.listarCategoriaProducto();
		
		map.put("productos", enviar);
		map.put("categorias", categoria);
		
		return map;
	}
	
	@GetMapping("/productos/{id}")
	public Producto listarId(@PathVariable Integer id) {
		return productoService.buscarIdProducto(id);
	}

	@PostMapping("/productos-imagen")
	public ResponseEntity<Object> guardarProductoImagen(Producto producto, @RequestParam("imagenes") MultipartFile multiPart){
			System.out.println("Body de la solicitud: " + producto);
			
			CategoriaProductos catPro = categoriaProducto.buscarIdProducto(producto.getCategoriaProductos().getId());
			
			if(catPro == null) {
				return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ LA CATEGORÍA");
			}
			
				TipoProducto tipPro = tipoProducto.buscarIdProducto(producto.getTipoProducto().getId());
			
			if(tipPro == null) {
				return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ LA TIPO");
			}
			
			if(!multiPart.isEmpty()) {
				
				String nombreImagen = Utilidades.guardarArchivo(multiPart);
				if(nombreImagen=="no") {
					return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "ARCHIVO NO VALIDO");
				}
				producto.setStock(0);
				producto.setImagen(nombreImagen);
				productoService.guardar(producto);
				return Utilidades.generateResponseTrue(HttpStatus.CREATED, "PRODUCTO CREADO CORRECTAMENTE");
				
				
				
			}else {
				return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO HAY IMAGEN");
			}
		
	}
	@PutMapping("/productos-imagen")
	public ResponseEntity<Object> actualizarProductoImagen(Producto producto, @RequestParam("imagenes") MultipartFile multiPart){
			
			CategoriaProductos catPro = categoriaProducto.buscarIdProducto(producto.getCategoriaProductos().getId());
			System.out.println(catPro);
			if(catPro == null) {
				return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ LA CATEGORÍA");
			}
			
			Producto pr = productoService.buscarIdProducto(producto.getId());
			System.out.println(pr);
			if (pr == null) {
				return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "EL PRODUCTO NO SE ENCONTRÓ");
			}
			
			if(!multiPart.isEmpty()) {
				
				String nombreImagen = Utilidades.guardarArchivo(multiPart);
				if(nombreImagen=="no") {
					return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "ARCHIVO NO VALIDO");
				}
				
				producto.setStock(producto.getStock());
				producto.setImagen(nombreImagen);
				producto.setFecha(new Date());
				productoService.guardar(producto);
				return Utilidades.generateResponseTrue(HttpStatus.CREATED, "PRODUCTO ACTUALIZADO CORRECTAMENTE");
				
				
				
			}else {
				System.out.println(pr.getStock());
				producto.setStock(pr.getStock());
				producto.setImagen(pr.getImagen());
				producto.setFecha(new Date());
				productoService.guardar(producto);
				return Utilidades.generateResponseTrue(HttpStatus.CREATED, "PRODUCTO ACTUALIZADO CORRECTAMENTE");
				
			}
		
	}

	@PostMapping("/productos")
	public ResponseEntity<Object> guardarProducto(@RequestBody Producto producto){

			CategoriaProductos catPro = categoriaProducto.buscarIdProducto(producto.getCategoriaProductos().getId());
			if(catPro == null) {
				return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ LA CATEGORÍA");
			}
			
			TipoProducto tipPro = tipoProducto.buscarIdProducto(producto.getTipoProducto().getId());
			
			if(tipPro == null) {
				return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ LA TIPO");
			}

			producto.setStock(0);
			producto.setImagen(producto.getImagen());
			productoService.guardar(producto);
			
			return Utilidades.generateResponseTrue(HttpStatus.CREATED, "PRODUCTO CREADO CORRECTAMENTE");
		
	}

	@PutMapping("/productos")
	public ResponseEntity<Object> actualizarProducto(@RequestBody Producto producto) {

		Producto pr = productoService.buscarIdProducto(producto.getId());
				
		if (pr == null) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "EL PRODUCTO NO SE ENCONTRÃ“");
		}
		
		producto.setStock(pr.getStock());
		producto.setImagen(producto.getImagen());
		productoService.guardar(producto);

		return Utilidades.generateResponseTrue(HttpStatus.CREATED, "PRODUCTO ACTUALIZADO CORRECTAMENTE");
	}

	@PostMapping("/eliminar")
	public ResponseEntity<Object> eliminarProducto(@RequestBody Producto producto) {

		Producto pr = productoService.buscarIdProducto(producto.getId());

		if (pr == null) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "EL PRODUCTO NO SE ENCONTRÓ");
		}
		productoService.eliminar(producto.getId());

		return Utilidades.generateResponseTrue(HttpStatus.OK, "PRODUCTO SE ELIMINÓ CORRECTAMENTE");
	}
	
	@GetMapping(value = "/images/{image}", produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<Resource> getImage(@PathVariable String image) throws IOException {
        Path rutaArchivo = Paths.get("imagenes").resolve(image).toAbsolutePath();
        
        Resource recurso = null;
        
        try {
        	recurso = new UrlResource(rutaArchivo.toUri());
        	
        }catch(MalformedURLException e){
        	e.printStackTrace();
        }
        HttpHeaders cabecera = new HttpHeaders();
        
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");
        
        
        return new ResponseEntity<Resource>(recurso,cabecera,HttpStatus.ACCEPTED);
    }	
	
}
