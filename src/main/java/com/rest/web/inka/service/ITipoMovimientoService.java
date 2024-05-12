package com.rest.web.inka.service;

import org.springframework.data.domain.Pageable;

import com.rest.web.inka.models.TipoMovimiento;
import com.rest.web.inka.models.TipoMovimientoDto;
import com.rest.web.inka.utilidades.PaginationMod;

public interface ITipoMovimientoService {
	
	PaginationMod<TipoMovimientoDto> listarTipoMovimientoDtoPaginado(String nombre, Pageable pageable);

	TipoMovimiento buscarIdTipoMovimiento(Integer id);
	
	TipoMovimiento guardar(TipoMovimiento tipoMovimiento);
	
	void eliminar(Integer id);
}
