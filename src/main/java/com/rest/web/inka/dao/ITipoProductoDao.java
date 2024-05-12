package com.rest.web.inka.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.web.inka.models.TipoProducto;

public interface ITipoProductoDao extends JpaRepository<TipoProducto, Integer>{
	public Page<TipoProducto> findByNombreContaining(String nombre, Pageable pageable);
}