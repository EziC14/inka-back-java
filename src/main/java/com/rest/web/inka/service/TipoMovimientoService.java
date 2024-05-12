package com.rest.web.inka.service;

import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rest.web.inka.dao.IMovimientoDao;
import com.rest.web.inka.dao.ITipoMovimientoDao;
import com.rest.web.inka.models.Movimiento;
import com.rest.web.inka.models.MovimientoDto;
import com.rest.web.inka.models.TipoMovimiento;
import com.rest.web.inka.models.TipoMovimientoDto;
import com.rest.web.inka.utilidades.PaginationMod;

@Service
public class TipoMovimientoService implements ITipoMovimientoService{
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private ITipoMovimientoDao tipoMovimientoDao;
	
	@Override
	public PaginationMod<TipoMovimientoDto> listarTipoMovimientoDtoPaginado(String nombre, Pageable pageable) {
		Page<TipoMovimiento> paginacion = tipoMovimientoDao.findByNombreContaining(nombre, pageable);
		
		PaginationMod<TipoMovimientoDto> paginationMod = new PaginationMod<TipoMovimientoDto>();
		TipoMovimientoDto[] entityDtos = mapper.map(paginacion.getContent(),TipoMovimientoDto[].class);
		paginationMod.setValue(paginacion, Arrays.asList(entityDtos));
		return paginationMod;
	}
	

	@Override
	public TipoMovimiento buscarIdTipoMovimiento(Integer id) {
		return tipoMovimientoDao.findById(id).orElse(null);
	}

	@Override
	public TipoMovimiento guardar(TipoMovimiento TipoMovimiento) {
		return tipoMovimientoDao.save(TipoMovimiento);
	}

	@Override
	public void eliminar(Integer id) {
		tipoMovimientoDao.deleteById(id);	
	}
}
