package com.rest.web.inka.jwt;

public class AuthResponse {

	private String correo;
	private String accessToken;
	
	
	
	public AuthResponse(String correo, String accessToken) {
		this.correo = correo;
		this.accessToken = accessToken;
	}
	public AuthResponse() {
		
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
