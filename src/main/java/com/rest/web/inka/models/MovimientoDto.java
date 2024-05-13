package com.rest.web.inka.models;

import java.io.Serializable;
import java.util.Date;


public class MovimientoDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String nombre;
	private String cantidad;

	private String motivo;
	
	private Date fecha;
	
	private UsuarioDto usuario;
	
	private ProvedorDto provedor;

	private ProductoDto producto;
	
	private TipoMovimientoDto tipo;

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

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

	public ProvedorDto getProvedor() {
		return provedor;
	}

	public void setProvedor(ProvedorDto provedor) {
		this.provedor = provedor;
	}

	public ProductoDto getProducto() {
		return producto;
	}

	public void setProducto(ProductoDto producto) {
		this.producto = producto;
	}

	public TipoMovimientoDto getTipo() {
		return tipo;
	}

	public void setTipo(TipoMovimientoDto tipo) {
		this.tipo = tipo;
	}
	
	
}
