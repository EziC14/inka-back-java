package com.rest.web.inka.models;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="producto")
public class Producto implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	private String nombre;
	
	@NotNull
	private String medida;
	
	@NotNull
	private String descripcion;

	@NotNull
	@DecimalMin(value = "1.0", message = "Please Enter a valid Deposit Amount")
	private Double precio;
	
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@OneToOne()
	@JoinColumn(name="categoria_id")
	private CategoriaProductos categoriaProductos;	
	
	@OneToOne()
	@JoinColumn(name="tipo_producto_id")
	private TipoProducto tipoProducto;	
	
//	@OneToOne()
//	@JoinColumn(name="color_id")
//	private ColorProducto colorProducto;	
	
	@Column(name="cantidad")
	private Integer stock;
	
	private String imagen;
	
	@PrePersist
	public void prePersist() {
		fecha = new Date();
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public CategoriaProductos getCategoriaProductos() {
		return categoriaProductos;
	}

	public void setCategoriaProductos(CategoriaProductos categoriaProductos) {
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

	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

//	public ColorProducto getColorProducto() {
//		return colorProducto;
//	}
//
//	public void setColorProducto(ColorProducto colorProducto) {
//		this.colorProducto = colorProducto;
//	}
//	
	
	
}
