package com.rest.web.inka.models;

import java.io.Serializable;

public class ProvedorDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	private String nombre;
	
	private String ruc;

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

