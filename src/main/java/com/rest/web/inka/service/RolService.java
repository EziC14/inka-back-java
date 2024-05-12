package com.rest.web.inka.service;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.web.inka.dao.IRolDao;
import com.rest.web.inka.models.Rol;
import com.rest.web.inka.models.RolDto;



@Service
public class RolService implements IRolService{
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private IRolDao rolDao;
	
	@Override
	public List<RolDto> listarRoles() {
		
		List<Rol> rol = rolDao.findAll();
		
		List<RolDto> dto = Arrays.asList(mapper.map(rol, RolDto[].class));
		
		return dto;
	}

	@Override
	public RolDto buscarRolDtoId(Integer id) {
		
		Rol rol = rolDao.findById(id).orElse(null);
		
		RolDto dto = mapper.map(rol, RolDto.class);
		
		return dto;
	}

	@Override
	public Rol guardar(Rol rol) {
		return rolDao.save(rol);
	}

	@Override
	public void eliminar(Integer id) {
		rolDao.deleteById(id);
	}

}
