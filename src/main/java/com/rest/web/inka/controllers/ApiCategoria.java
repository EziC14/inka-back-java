package com.rest.web.inka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

import com.rest.web.inka.models.CategoriaProductos;
import com.rest.web.inka.models.CategoriaProductosDto;
import com.rest.web.inka.service.ICategoriaProductoService;
import com.rest.web.inka.utilidades.PaginationMod;
import com.rest.web.inka.utilidades.Utilidades;

@RestController
@RequestMapping("/api/categoria")
public class ApiCategoria {
	
	@Autowired
	private ICategoriaProductoService categoriaService;
	
	@GetMapping("/listar")
	public PaginationMod<CategoriaProductosDto> listar(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "") String nombre) {
		return categoriaService.listarCategoriaProductoDtoPaginado(nombre,PageRequest.of(page, 5, Sort.by("id").descending()));
	}
	
	@GetMapping("/categoria/{id}")
	public ResponseEntity<Object> listarId(@PathVariable Integer id) {
		CategoriaProductos cate = categoriaService.buscarIdProducto(id);
		if(cate == null) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCUENTRA LA CATEGORÍA");
		}
		
		return ResponseEntity.ok(cate);
	}
	
	@PostMapping("/categoria")
	public ResponseEntity<Object> guardarCategoria(@RequestBody CategoriaProductos categoria){

			String url = Utilidades.getUrl(categoria.getNombre());

			
			if(categoriaService.buscarPorUrl(url) == false) {
				return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "LA CATEGORÍA YA SE ENCUENTRA CREADA");
			}
			
			categoria.setUrl(url);
			categoria.setNombre(categoria.getNombre().toUpperCase());
			categoriaService.guardar(categoria);
			return Utilidades.generateResponseTrue(HttpStatus.CREATED, "CATEGORÍA CREADA CORRECTAMENTE");
		
	}
	
	@PutMapping("/categoria")
	public ResponseEntity<Object> actualizarCategoria(@RequestBody CategoriaProductos categoria) {

		String url = Utilidades.getUrl(categoria.getNombre());
				
		if (categoriaService.buscarPorUrl(url) == false) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "LA CATEGORÍA YA SE ENCUENTRA CREADA");
		}
		categoria.setUrl(url);
		categoria.setNombre(categoria.getNombre().toUpperCase());
		categoriaService.guardar(categoria);
		return Utilidades.generateResponseTrue(HttpStatus.CREATED, "CATEGORÍA ACTUALIZADA CORRECTAMENTE");
	}	
	
	@PostMapping("/eliminar")
	public ResponseEntity<Object> eliminarCategoria(@RequestBody CategoriaProductos categoria) {

		CategoriaProductos cate = categoriaService.buscarIdProducto(categoria.getId());
		if(cate == null) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCUENTRA LA CATEGORÍA");
		}
		
		categoriaService.eliminar(categoria.getId());

		return Utilidades.generateResponseTrue(HttpStatus.OK, "LA CATEGORÍA SE ELIMINÓ CORRECTAMENTE");
	}
}
