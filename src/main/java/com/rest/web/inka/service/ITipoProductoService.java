package com.rest.web.inka.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.rest.web.inka.models.TipoProducto;
import com.rest.web.inka.models.TipoProductoDto;
import com.rest.web.inka.utilidades.PaginationMod;

public interface ITipoProductoService {
	
	TipoProducto buscarIdProducto(Integer id);
	
//	TipoProducto buscarIdProductoTipo(Integer id);
	
	PaginationMod<TipoProductoDto> listarTipoProductoDtoPaginado(String nombre, Pageable pageable);

	List<TipoProducto>  listarTipoProducto();
	
//	boolean buscarPorUrl(String url);
	
	TipoProducto guardar(TipoProducto categoria);
	
	TipoProducto buscarPorId(Integer id);
	
	void eliminar(Integer id);
}
