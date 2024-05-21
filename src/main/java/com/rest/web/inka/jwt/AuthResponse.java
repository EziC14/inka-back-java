package com.rest.web.inka.jwt;

public class AuthResponse {

	private String correo;
	private String accessToken;
	private String nombre;
	private String apellido;
	private String telefono;
	private String rol;
	
	
	
	public AuthResponse(String nombre, String apellido, String telefono ,String correo, String accessToken, String rol) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.correo = correo;
		this.accessToken = accessToken;
		this.rol = rol;
	}
	public AuthResponse() {
		
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	
	
}
