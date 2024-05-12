package com.rest.web.inka.service;

import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rest.web.inka.dao.IMovimientoDao;
import com.rest.web.inka.dao.IProvedorDao;
import com.rest.web.inka.models.Movimiento;
import com.rest.web.inka.models.MovimientoDto;
import com.rest.web.inka.models.Provedor;
import com.rest.web.inka.models.ProvedorDto;
import com.rest.web.inka.utilidades.PaginationMod;

@Service
public class ProvedorService implements IProvedorService{
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private IProvedorDao provedorDao;
	
	@Override
	public PaginationMod<ProvedorDto> listarProvedorDtoPaginado(String nombre, Pageable pageable) {
		Page<Provedor> paginacion = provedorDao.findByNombreContaining(nombre, pageable);
		
		PaginationMod<ProvedorDto> paginationMod = new PaginationMod<ProvedorDto>();
		ProvedorDto[] entityDtos = mapper.map(paginacion.getContent(),ProvedorDto[].class);
		paginationMod.setValue(paginacion, Arrays.asList(entityDtos));
		return paginationMod;
	}
	

	@Override
	public Provedor buscarIdProvedor(Integer id) {
		return provedorDao.findById(id).orElse(null);
	}

	@Override
	public Provedor guardar(Provedor Provedor) {
		return provedorDao.save(Provedor);
	}

	@Override
	public void eliminar(Integer id) {
		provedorDao.deleteById(id);	
	}
}
