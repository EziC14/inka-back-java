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

import com.rest.web.inka.models.Estado;
import com.rest.web.inka.service.IEstadoService;
import com.rest.web.inka.utilidades.Utilidades;

@RestController
@RequestMapping("/api/estados")
public class ApiEstados {

	@Autowired
	private IEstadoService estadoService;
	@GetMapping("/listar")
	
	public List<Estado> estados() {
		return estadoService.listarEstados();
	}

	@GetMapping("/listar/{id}")
	public Estado estadosId(@PathVariable Integer id) {
		return estadoService.buscarEstadoId(id);
	}

	@PostMapping("/estado")
	public ResponseEntity<Object> estadoGuardar(@RequestBody Estado estado) {
		estadoService.guardar(estado);
		return Utilidades.generateResponseTrue(HttpStatus.CREATED, "ESTADO CREADO CORRECTAMENTE");
	}

	@PutMapping("/estado")
	public ResponseEntity<Object> rolesActualizar(@RequestBody Estado estado) {

		Estado estadoBuscar = estadoService.buscarEstadoId(estado.getId());

		if (estadoBuscar == null) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL ESTADO");
		}

		estadoService.guardar(estado);
		return Utilidades.generateResponseTrue(HttpStatus.CREATED, "ESTADO ACTUALIZADO CORRECTAMENTE");
	}

	@PostMapping("/eliminar")
	public ResponseEntity<Object> eliminar(@RequestBody Estado estado) {
		Estado estadoBuscar = estadoService.buscarEstadoId(estado.getId());

		if (estadoBuscar == null) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL ESTADO");
		}
		
		estadoService.eliminar(estado.getId());
		return Utilidades.generateResponseTrue(HttpStatus.OK, "ESTADO ELIMINADO CORRECTAMENTE");
	}
}
