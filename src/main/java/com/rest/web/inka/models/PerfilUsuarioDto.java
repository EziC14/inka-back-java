package com.rest.web.inka.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PerfilUsuarioDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String nombres;
	
	private String apellidos;
	
	private String telefono;
	@JsonIgnore
	private UsuarioDto usuario;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}
	
	
	
}
