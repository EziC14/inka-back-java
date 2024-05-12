package com.rest.web.inka.service;

import java.util.List;

import com.rest.web.inka.models.Rol;
import com.rest.web.inka.models.RolDto;

public interface IRolService {

	List<RolDto> listarRoles();
	
	RolDto buscarRolDtoId(Integer id);
	
	Rol guardar(Rol rol);
	
	void eliminar(Integer id);
	
}
