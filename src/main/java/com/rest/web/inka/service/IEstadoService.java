package com.rest.web.inka.service;

import java.util.List;

import com.rest.web.inka.models.Estado;


public interface IEstadoService {
	
	List<Estado> listarEstados();
	
	Estado buscarEstadoId(Integer id);
	
	Estado guardar(Estado estado);
	
	void eliminar(Integer id);
}
