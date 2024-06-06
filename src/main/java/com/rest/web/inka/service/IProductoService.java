package com.rest.web.inka.service;

import org.springframework.data.domain.Pageable;

import com.rest.web.inka.models.Producto;
import com.rest.web.inka.models.ProductoDto;
import com.rest.web.inka.utilidades.PaginationMod;

public interface IProductoService {
		 
	PaginationMod<ProductoDto> listarProductoDtoPaginado(String nombre, Pageable pageable);

	PaginationMod<ProductoDto> buscarProductoPorNombrePaginado(String nombre, Pageable pageable);

	Producto buscarIdProducto(Integer id);
	
	Producto guardar(Producto producto);
	
	void eliminar(Integer id);

}
