package com.rest.web.inka.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.web.inka.models.Usuario;

public interface IUsuarioDao extends JpaRepository<Usuario, Integer>{
	
	Optional<Usuario> findByCorreo(String username);
	
	Page<Usuario> findByCorreoContaining(String username, Pageable pageable);
	
}
