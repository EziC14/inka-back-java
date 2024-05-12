package com.rest.web.inka.models;

import java.io.Serializable;
import java.util.Set;

public class UsuarioDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String correo;
	
	private Set<RolDto> roles;
	
	private EstadoDto estado;
	
	private PerfilUsuarioDto perfilUsuario;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Set<RolDto> getRoles() {
		return roles;
	}

	public void setRoles(Set<RolDto> roles) {
		this.roles = roles;
	}

	public EstadoDto getEstado() {
		return estado;
	}

	public void setEstado(EstadoDto estado) {
		this.estado = estado;
	}

	public PerfilUsuarioDto getPerfilUsuario() {
		return perfilUsuario;
	}

	public void setPerfilUsuario(PerfilUsuarioDto perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}

	
	
}
