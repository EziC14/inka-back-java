package com.rest.web.inka.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.web.inka.jwt.AuthRequest;
import com.rest.web.inka.jwt.AuthResponse;
import com.rest.web.inka.jwt.JwtTokenUtil;
import com.rest.web.inka.models.Usuario;
import com.rest.web.inka.service.IUsuarioService;

@RestController
@RequestMapping("/api/v1")
public class AccesoController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtTokenUtil jwtUtil;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest request)
	{
		try {
			
			Authentication authentication = this.authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getPassword()));
			System.out.println(authentication);
			
			Optional<Usuario> user = usuarioService.buscarPorCorreo(request.getCorreo());
			
			String accessToken = jwtUtil.generarToken(user.get());
						
			AuthResponse response = new AuthResponse(request.getCorreo(), accessToken);
			
			return ResponseEntity.ok(response);
			
		} catch (BadCredentialsException e) {
			
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}
