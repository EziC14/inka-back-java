package com.rest.web.inka.service;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rest.web.inka.dao.IMovimientoDao;
import com.rest.web.inka.models.Movimiento;
import com.rest.web.inka.models.MovimientoDto;
import com.rest.web.inka.utilidades.PaginationMod;

@Service
public class MovimientoService implements IMovimientoService {
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private IMovimientoDao movimientoDao;
	
	@Override
	public PaginationMod<MovimientoDto> listarMovimientoDtoPaginado(String nombre, Date dateFrom, Date dateTo, Pageable pageable) {
	    Page<Movimiento> paginacion;

	    if (dateFrom != null && dateTo != null) {
	        paginacion = movimientoDao.findByNombreContainingAndFechaBetween(nombre, dateFrom, dateTo, pageable);
	    } else if (dateFrom != null) {
	        paginacion = movimientoDao.findByNombreContainingAndFechaAfter(nombre, dateFrom, pageable);
	    } else if (dateTo != null) {
	        paginacion = movimientoDao.findByNombreContainingAndFechaBefore(nombre, dateTo, pageable);
	    } else {
	        paginacion = movimientoDao.findByNombreContaining(nombre, pageable);
	    }

	    PaginationMod<MovimientoDto> paginationMod = new PaginationMod<MovimientoDto>();
	    MovimientoDto[] entityDtos = mapper.map(paginacion.getContent(), MovimientoDto[].class);
	    paginationMod.setValue(paginacion, Arrays.asList(entityDtos));
	    return paginationMod;
	}

	

	@Override
	public Movimiento buscarIdMovimiento(Integer id) {
		return movimientoDao.findById(id).orElse(null);
	}

	@Override
	public Movimiento guardar(Movimiento movimiento) {
		return movimientoDao.save(movimiento);
	}

	@Override
	public void eliminar(Integer id) {
		movimientoDao.deleteById(id);	
	}

	@Override
	public List<Movimiento> getListMovimiento() {
		return movimientoDao.findAll();
	}
}
