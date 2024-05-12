package com.rest.web.inka.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.rest.web.inka.models.CategoriaProductos;
import com.rest.web.inka.models.CategoriaProductosDto;
import com.rest.web.inka.utilidades.PaginationMod;

public interface ICategoriaProductoService {
	
	CategoriaProductos buscarIdProducto(Integer id);
	
	PaginationMod<CategoriaProductosDto> listarCategoriaProductoDtoPaginado(String nombre, Pageable pageable);

	List<CategoriaProductos>  listarCategoriaProducto();
	
	boolean buscarPorUrl(String url);
	
	CategoriaProductos guardar(CategoriaProductos categoria);
	
	CategoriaProductos buscarPorId(Integer id);
	
	void eliminar(Integer id);
}
