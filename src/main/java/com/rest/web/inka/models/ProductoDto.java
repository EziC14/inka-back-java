package com.rest.web.inka.models;

import java.io.Serializable;


public class ProductoDto implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String nombre;
	
	private String medida;
	
	private String descripcion;

	private Double precio;
	
	private Integer stock;
	
	private String imagen;
	
	private CategoriaProductosDto categoriaProductos;	
	
	private TipoProductoDto tipoProducto;	
	
	private ColorProducto colorProducto;
	
	public ProductoDto() {
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


	public Double getPrecio() {
		return precio;
	}


	public void setPrecio(Double precio) {
		this.precio = precio;
	}


	public CategoriaProductosDto getCategoriaProductos() {
		return categoriaProductos;
	}


	public void setCategoriaProductos(CategoriaProductosDto categoriaProductos) {
		this.categoriaProductos = categoriaProductos;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getMedida() {
		return medida;
	}

	public void setMedida(String medida) {
		this.medida = medida;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public TipoProductoDto getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(TipoProductoDto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

	public ColorProducto getColorProducto() {
		return colorProducto;
	}

	public void setColorProducto(ColorProducto colorProducto) {
		this.colorProducto = colorProducto;
	}
	
	
	
}
