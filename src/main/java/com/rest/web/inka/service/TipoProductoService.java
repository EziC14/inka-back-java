package com.rest.web.inka.service;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rest.web.inka.dao.ICategoriaProductoDao;
import com.rest.web.inka.dao.ITipoProductoDao;
import com.rest.web.inka.models.CategoriaProductos;
import com.rest.web.inka.models.CategoriaProductosDto;
import com.rest.web.inka.models.TipoProducto;
import com.rest.web.inka.models.TipoProductoDto;
import com.rest.web.inka.utilidades.PaginationMod;

@Service
public class TipoProductoService implements ITipoProductoService{
	
	@Autowired
	private ITipoProductoDao tipoDao;

	@Autowired
	private ModelMapper mapper;
	
	@Override
	public TipoProducto buscarIdProducto(Integer id) {
		return tipoDao.findById(id).orElse(null);
	}

	@Override
	public PaginationMod<TipoProductoDto> listarTipoProductoDtoPaginado(String nombre, Pageable pageable) {
		Page<TipoProducto> paginacion = tipoDao.findByNombreContaining(nombre, pageable);
		
		PaginationMod<TipoProductoDto> paginationMod = new PaginationMod<TipoProductoDto>();
		TipoProductoDto[] entityDtos = mapper.map(paginacion.getContent(),TipoProductoDto[].class);
		paginationMod.setValue(paginacion, Arrays.asList(entityDtos));
		return paginationMod;
	}
	

	@Override
	public TipoProducto guardar(TipoProducto tipo) {
		return tipoDao.save(tipo);
	}

	@Override
	public TipoProducto buscarPorId(Integer id) {
		return tipoDao.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Integer id) {
		tipoDao.deleteById(id);
	}

	@Override
	public List<TipoProducto> listarTipoProducto() {
		return tipoDao.findAll();
	}
	
}
