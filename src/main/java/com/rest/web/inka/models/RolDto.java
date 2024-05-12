package com.rest.web.inka.models;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RolDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String nombre;
	@JsonIgnore
	private Set<UsuarioDto> usuario;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<UsuarioDto> getUsuario() {
		return usuario;
	}

	public void setUsuario(Set<UsuarioDto> usuario) {
		this.usuario = usuario;
	}

	
	
	
}
