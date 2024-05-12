package com.rest.web.inka.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.web.inka.models.CategoriaProductos;

public interface ICategoriaProductoDao extends JpaRepository<CategoriaProductos, Integer>{
	public Page<CategoriaProductos> findByNombreContaining(String nombre, Pageable pageable);
	public boolean existsByUrl(String url);
}
