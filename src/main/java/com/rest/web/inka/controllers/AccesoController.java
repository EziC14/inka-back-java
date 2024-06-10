package com.rest.web.inka.controllers;

import java.util.Optional;

import org.modelmapper.ModelMapper;
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

import com.rest.web.inka.dao.IUsuarioDao;
import com.rest.web.inka.jwt.AuthRequest;
import com.rest.web.inka.jwt.AuthResponse;
import com.rest.web.inka.jwt.JwtTokenUtil;
import com.rest.web.inka.models.RolDto;
import com.rest.web.inka.models.Usuario;
import com.rest.web.inka.models.UsuarioDto;
import com.rest.web.inka.service.IUsuarioService;

import jakarta.persistence.criteria.CriteriaBuilder.In;

@RestController
@RequestMapping("/api/v1")
public class AccesoController {
	
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private JwtTokenUtil jwtUtil;
	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private IUsuarioDao usuarioDao;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest request)
	{
		try {
			Authentication authentication = this.authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getPassword()));
			System.out.println(authentication);
			Optional<Usuario> user = usuarioService.buscarPorCorreo(request.getCorreo());
			Integer id = user.get().getId();
			Usuario usu =  usuarioDao.findById(id).orElse(null);
			UsuarioDto dto = mapper.map(usu, UsuarioDto.class);
			Integer idUsuario = dto.getId();
			String nombre = dto.getPerfilUsuario().getNombres(); 
			String apellido = dto.getPerfilUsuario().getApellidos();
			String telefono = dto.getPerfilUsuario().getTelefono();
			String rol_name = "";
			for (RolDto rol : dto.getRoles()) {
				rol_name = rol.getNombre();
			}
			String accessToken = jwtUtil.generarToken(user.get());			
			AuthResponse response = new AuthResponse(idUsuario,nombre,apellido,telefono,request.getCorreo(), accessToken, rol_name);
			return ResponseEntity.ok(response);		
		} catch (BadCredentialsException e) {	
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}
