package com.rest.web.inka.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.web.inka.models.Producto;
import com.rest.web.inka.models.Provedor;

public interface IProvedorDao extends JpaRepository<Provedor, Integer>{
	public Page<Provedor> findByNombreContaining(String title, Pageable pageable);
}
