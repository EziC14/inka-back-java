package com.rest.web.inka.models;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="almacen_item")
public class ItemAlmacen   implements Serializable{
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	

	private Integer cantidad;

	@OneToOne()
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
	private Producto producto;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="almacen_id")
	@JsonIgnore
	private Almacen almacen;
	
	
	
	
	
	public ItemAlmacen() {
	}

	public Double calcularImporte() {
		return cantidad.doubleValue() * producto.getPrecio();
	}

	public Double getTotalProductoItem() {
		
		return cantidad.doubleValue() * producto.getPrecio();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}


	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Almacen getAlmacen() {
		return almacen;
	}

	public void setPedido(Almacen almacen) {
		this.almacen = almacen;
	}
	
	
}
