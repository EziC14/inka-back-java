package com.rest.web.inka.models;

import java.io.Serializable;

public class TipoMovimientoDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String nombre;

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
	
	
}
