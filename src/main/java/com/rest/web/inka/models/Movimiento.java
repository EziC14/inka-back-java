package com.rest.web.inka.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Table(name="movimiento")
public class Movimiento {
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	

	private String cantidad;

	private String motivo;
	
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	//@JsonManagedReference
	@OneToOne()
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
	private Usuario usuario;
	
//	//@JsonManagedReference
//	@OneToOne()
//    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
//	private Usuario usuarioCliente;
	
	
	@OneToOne()
    @JoinColumn(name = "provedor_id", referencedColumnName = "id")
	private Provedor provedor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="producto_id")
	@JsonIgnore
	private Producto producto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="tipo_movimiento_id")
	@JsonIgnore
	private TipoMovimiento tipo;
	
	public Movimiento() {
	}
	
	public Movimiento(String cantidad, String motivo, Date fecha, Usuario usuario, Provedor provedor, Producto producto,
			TipoMovimiento tipo) {
		super();
		this.cantidad = cantidad;
		this.motivo = motivo;
		this.fecha = fecha;
		this.usuario = usuario;
		this.provedor = provedor;
		this.producto = producto;
		this.tipo = tipo;
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Provedor getProvedor() {
		return provedor;
	}

	public void setProvedor(Provedor provedor) {
		this.provedor = provedor;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public TipoMovimiento getTipo() {
		return tipo;
	}

	public void setTipo(TipoMovimiento tipo) {
		this.tipo = tipo;
	}

}
