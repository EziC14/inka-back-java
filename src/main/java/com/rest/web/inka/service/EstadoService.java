package com.rest.web.inka.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.web.inka.dao.IEstadosDao;
import com.rest.web.inka.models.Estado;

@Service
public class EstadoService implements IEstadoService{

	@Autowired
	private IEstadosDao estadoDao;
	
	@Override
	public List<Estado> listarEstados() {
		return estadoDao.findAll();
	}

	@Override
	public Estado buscarEstadoId(Integer id) {
		return estadoDao.findById(id).orElse(null);
	}

	@Override
	public Estado guardar(Estado estado) {
		return estadoDao.save(estado);
	}

	@Override
	public void eliminar(Integer id) {
		estadoDao.deleteById(id);		
	}

}
