package com.rest.web.inka.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rest.web.inka.models.PerfilUsuario;
import com.rest.web.inka.models.Usuario;
import com.rest.web.inka.models.UsuarioDto;
import com.rest.web.inka.service.IUsuarioService;
import com.rest.web.inka.utilidades.PaginationMod;
import com.rest.web.inka.utilidades.Utilidades;

@RestController
@RequestMapping("/api/usuarios")
public class ApiUsuarios {
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/listar")
	public PaginationMod<UsuarioDto> usuarios(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "") String nombre) {
				
		return usuarioService.listarProductoDtoPaginado(nombre,PageRequest.of(page, 5, Sort.by("id").descending()));
	}	
	
	@GetMapping("/listar/{id}")
	public UsuarioDto usuariosId(@PathVariable Integer id) {
		return usuarioService.buscarPorId(id);
	}
	
	@PostMapping("/usuario")
	public ResponseEntity<Object> guardarUsuario(@RequestBody Usuario usuario){

			Usuario guardarUsuario = new Usuario();
			guardarUsuario.setCorreo(usuario.getCorreo());
			guardarUsuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			guardarUsuario.setEstado(usuario.getEstado());
			guardarUsuario.setRoles(usuario.getRoles());			
			PerfilUsuario perfilGuardar = new PerfilUsuario();
			perfilGuardar.setNombres(usuario.getPerfilUsuario().getNombres());
			perfilGuardar.setApellidos(usuario.getPerfilUsuario().getApellidos());
			perfilGuardar.setTelefono(usuario.getPerfilUsuario().getTelefono());
			guardarUsuario.setPerfilUsuario(perfilGuardar);
			perfilGuardar.setUsuario(guardarUsuario);			
			usuarioService.guardar(guardarUsuario);
			return Utilidades.generateResponseTrue(HttpStatus.CREATED, "USUARIO CREADO");
		
	}
	@PutMapping("/usuario")
	public ResponseEntity<Object> actualizarUsuario(@RequestBody Usuario usuario){
			Usuario guardarUsuario = new Usuario();
			guardarUsuario.setId(usuario.getId());
			guardarUsuario.setCorreo(usuario.getCorreo());
			if(usuario.getPassword().isEmpty()) {
				Usuario user = usuarioService.buscarPorIdUsuario(usuario.getId());
				guardarUsuario.setPassword(user.getPassword());
			}else {
				guardarUsuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			}
			guardarUsuario.setEstado(usuario.getEstado());
			guardarUsuario.setRoles(usuario.getRoles());	
			PerfilUsuario perfilGuardar = new PerfilUsuario();
			perfilGuardar.setId(usuario.getPerfilUsuario().getId());
			perfilGuardar.setNombres(usuario.getPerfilUsuario().getNombres());
			perfilGuardar.setApellidos(usuario.getPerfilUsuario().getApellidos());
			perfilGuardar.setTelefono(usuario.getPerfilUsuario().getTelefono());
			guardarUsuario.setPerfilUsuario(perfilGuardar);
			perfilGuardar.setUsuario(guardarUsuario);
			usuarioService.guardar(guardarUsuario);
			return Utilidades.generateResponseTrue(HttpStatus.CREATED, "USUARIO ACTUALIZADO");
	}
	@PostMapping("/eliminar")
	public ResponseEntity<Object> eliminarUsuaroi(@RequestBody Usuario usuario){
			
			usuarioService.eliminar(usuario.getId());
			return Utilidades.generateResponseTrue(HttpStatus.CREATED, "USUARIO ELIMINADO");
		
	}
}
