package com.rest.web.inka.dao;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.web.inka.models.Movimiento;

public interface IMovimientoDao extends JpaRepository<Movimiento, Integer>{
	Page<Movimiento> findByNombreContaining(String nombre, Pageable pageable);
    Page<Movimiento> findByNombreContainingAndFechaBetween(String nombre, Date dateFrom, Date dateTo, Pageable pageable);
    Page<Movimiento> findByNombreContainingAndFechaAfter(String nombre, Date dateFrom, Pageable pageable);
    Page<Movimiento> findByNombreContainingAndFechaBefore(String nombre, Date dateTo, Pageable pageable);
}
