package com.rest.web.inka.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.web.inka.models.Producto;

public interface IProductoDao extends JpaRepository<Producto, Integer>{
	
	public Page<Producto> findByNombreContaining(String title, Pageable pageable);

}
