package com.rest.web.inka.service;

import org.springframework.data.domain.Pageable;

import com.rest.web.inka.models.Provedor;
import com.rest.web.inka.models.ProvedorDto;
import com.rest.web.inka.utilidades.PaginationMod;

public interface IProvedorService {
	PaginationMod<ProvedorDto> listarProvedorDtoPaginado(String nombre, Pageable pageable);

	Provedor buscarIdProvedor(Integer id);
	
	Provedor guardar(Provedor provedor);
	
	void eliminar(Integer id);
}
