package com.rest.web.inka.service;

import java.util.Arrays;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rest.web.inka.dao.IUsuarioDao;
import com.rest.web.inka.models.Usuario;
import com.rest.web.inka.models.UsuarioDto;
import com.rest.web.inka.utilidades.PaginationMod;

@Service
public class UsuarioService implements IUsuarioService {
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private IUsuarioDao usuarioDao;


	@Override
	public Optional<Usuario> buscarPorCorreo(String nombre) {
		return usuarioDao.findByCorreo(nombre);
	}

	@Override
	public Page<Usuario> listarUsuario(String nombre, Pageable pageable) {
		return usuarioDao.findByCorreoContaining(nombre, pageable);
	}

	@Override
	public UsuarioDto buscarPorId(Integer id) {
		Usuario usu =  usuarioDao.findById(id).orElse(null);
		
		UsuarioDto dto = mapper.map(usu, UsuarioDto.class);
		
		return dto;
	}

	@Override
	public PaginationMod<UsuarioDto> listarProductoDtoPaginado(String nombre, Pageable pageable) {
		Page<Usuario> paginacion = usuarioDao.findByCorreoContaining(nombre, pageable);
		
		PaginationMod<UsuarioDto> paginationMod = new PaginationMod<UsuarioDto>();
		UsuarioDto[] entityDtos = mapper.map(paginacion.getContent(),UsuarioDto[].class);
		paginationMod.setValue(paginacion, Arrays.asList(entityDtos));
		return paginationMod;
	}

	@Override
	public Usuario guardar(Usuario usuario) {
		return usuarioDao.save(usuario);
	}

	@Override
	public Usuario buscarPorIdUsuario(Integer id) {
		return usuarioDao.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Integer id) {
		usuarioDao.deleteById(id);
	}
}
