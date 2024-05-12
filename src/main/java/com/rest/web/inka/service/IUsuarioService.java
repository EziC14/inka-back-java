package com.rest.web.inka.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rest.web.inka.models.Usuario;
import com.rest.web.inka.models.UsuarioDto;
import com.rest.web.inka.utilidades.PaginationMod;

public interface IUsuarioService {

	Optional<Usuario> buscarPorCorreo(String nombre);
	
	UsuarioDto buscarPorId(Integer id);
	
	Usuario buscarPorIdUsuario(Integer id);
	
	Page<Usuario> listarUsuario(String nombre, Pageable pageable);
	
	PaginationMod<UsuarioDto> listarProductoDtoPaginado(String nombre, Pageable pageable);
	
	Usuario guardar(Usuario usuario);
	
	void eliminar(Integer id);
	
}
