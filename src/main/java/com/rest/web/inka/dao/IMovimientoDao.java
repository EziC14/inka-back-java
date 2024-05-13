package com.rest.web.inka.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.web.inka.models.Movimiento;

public interface IMovimientoDao extends JpaRepository<Movimiento, Integer>{
	public Page<Movimiento> findByNombreContaining(String title, Pageable pageable);
}
