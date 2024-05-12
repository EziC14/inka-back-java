package com.rest.web.inka.service;

import org.springframework.data.domain.Pageable;

import com.rest.web.inka.models.Movimiento;
import com.rest.web.inka.models.MovimientoDto;
import com.rest.web.inka.utilidades.PaginationMod;

public interface IMovimientoService {
	PaginationMod<MovimientoDto> listarMovimientoDtoPaginado(String nombre, Pageable pageable);

	Movimiento buscarIdMovimiento(Integer id);
	
	Movimiento guardar(Movimiento movimiento);
	
	void eliminar(Integer id);
}
