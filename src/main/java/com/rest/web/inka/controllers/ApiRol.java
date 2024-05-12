package com.rest.web.inka.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.web.inka.models.Rol;
import com.rest.web.inka.models.RolDto;
import com.rest.web.inka.service.IRolService;
import com.rest.web.inka.utilidades.Utilidades;

@RestController
@RequestMapping("/api/roles")
public class ApiRol {

	@Autowired
	private IRolService rolService;

	@GetMapping("/listar")
	public List<RolDto> roles() {
		return rolService.listarRoles();
	}

	@GetMapping("/listar/{id}")
	public RolDto rolesId(@PathVariable Integer id) {
		return rolService.buscarRolDtoId(id);
	}

	@PostMapping("/rol")
	public ResponseEntity<Object> rolesGuardar(@RequestBody RolDto dto) {

		Rol rolGuardar = new Rol();

		rolGuardar.setNombre(dto.getNombre());
		rolService.guardar(rolGuardar);
		return Utilidades.generateResponseTrue(HttpStatus.CREATED, "ROL CREADO CORRECTAMENTE");
	}

	@PutMapping("/rol")
	public ResponseEntity<Object> rolesActualizar(@RequestBody RolDto dto) {

		RolDto rolDto = rolService.buscarRolDtoId(dto.getId());

		if (rolDto == null) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL ROL");
		}

		Rol rolGuardar = new Rol();

		rolGuardar.setId(dto.getId());
		rolGuardar.setNombre(dto.getNombre());
		rolService.guardar(rolGuardar);
		return Utilidades.generateResponseTrue(HttpStatus.CREATED, "ROL ACTUALIZADO CORRECTAMENTE");
	}

	@PostMapping("/eliminar")
	public ResponseEntity<Object> eliminar(@RequestBody RolDto dto) {
		RolDto rolDto = rolService.buscarRolDtoId(dto.getId());

		if (rolDto == null) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL ROL");
		}
		
		rolService.eliminar(dto.getId());
		return Utilidades.generateResponseTrue(HttpStatus.OK, "ROL ELIMINADO CORRECTAMENTE");
	}
}
