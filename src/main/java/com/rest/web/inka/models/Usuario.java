package com.rest.web.inka.models;


import java.io.Serializable;
import java.util.Date;
import java.util.Set;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Table(name="usuarios")
@Entity
public class Usuario  implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String correo;
	
	private String password;
	
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@ManyToMany(fetch = FetchType.EAGER)
	//@JsonManagedReference
	@JoinTable(
			name="rol_user", 
			joinColumns = { @JoinColumn(name="usuario_id") }, 
			inverseJoinColumns = {@JoinColumn(name="rol_id")})
	private Set<Rol> roles;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="estado_id")
	private Estado estado;
	
	@OneToOne(cascade = {
	          CascadeType.PERSIST,
	          CascadeType.MERGE
	      },fetch = FetchType.EAGER,mappedBy = "usuario", orphanRemoval = true)
	private PerfilUsuario perfilUsuario;
	
	
	@PrePersist
	public void prePersist() {
		fecha = new Date();
	}
	public Usuario() {

	}

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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Set<Rol> getRoles() {
		return roles;
	}
	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public PerfilUsuario getPerfilUsuario() {
		return perfilUsuario;
	}
	public void setPerfilUsuario(PerfilUsuario perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}
	public void addRoles(Rol itemRol) {
		roles.add(itemRol);
	}
}
