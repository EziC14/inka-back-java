package com.rest.web.inka.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.web.inka.models.Producto;
import com.rest.web.inka.models.TipoMovimiento;

public interface ITipoMovimientoDao extends JpaRepository<TipoMovimiento, Integer>{
	public Page<TipoMovimiento> findByNombreContaining(String title, Pageable pageable);
}
