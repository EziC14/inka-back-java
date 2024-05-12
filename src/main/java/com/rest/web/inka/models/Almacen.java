package com.rest.web.inka.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="almacen")
public class Almacen  implements Serializable {
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	

	private String descripcion;

	private String observacion;
	
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	//@JsonManagedReference
	@OneToOne()
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
	private Usuario usuario;
	
	//@JsonManagedReference
	@OneToOne()
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
	private Usuario usuarioCliente;
	
	
	@OneToMany(mappedBy = "almacen",cascade = CascadeType.ALL)
	private List<ItemAlmacen> itemsAlmacen;
	

	
	public Almacen() {
		this.itemsAlmacen = new ArrayList<ItemAlmacen>();
	}
	
	
	@PrePersist
	public void prePersist() {
		fecha = new Date();
	}

	public void addItemPedido(ItemAlmacen itemAlmacen) {
		itemsAlmacen.add(itemAlmacen);
	}
	
	public Double getTotal() {
		Double total = 0.0;

		int size = itemsAlmacen.size();

		for (int i = 0; i < size; i++) {
			total += itemsAlmacen.get(i).calcularImporte();
		}
		return total;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
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


	public List<ItemAlmacen> getItemsAlmacen() {
		return itemsAlmacen;
	}


	public void setItemsAlmacen(List<ItemAlmacen> itemsAlmacen) {
		this.itemsAlmacen = itemsAlmacen;
	}


	public Usuario getUsuarioCliente() {
		return usuarioCliente;
	}


	public void setUsuarioCliente(Usuario usuarioCliente) {
		this.usuarioCliente = usuarioCliente;
	}



	
}
