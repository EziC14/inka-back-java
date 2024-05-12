package com.rest.web.inka.models;

import java.io.Serializable;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="rol")
public class Rol  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nombre;
	
	@ManyToMany(mappedBy="roles",cascade = {
	          CascadeType.PERSIST,
	          CascadeType.MERGE
	      })
	//@JsonBackReference
	@JsonIgnore
	private Set<Usuario> usuario;

	
	

	public Rol() {
	}

	public Rol(String nombre) {
		this.nombre = nombre;
	}



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

	public Set<Usuario> getUsuario() {
		return usuario;
	}

	public void setUsuario(Set<Usuario> usuario) {
		this.usuario = usuario;
	}
	
}
