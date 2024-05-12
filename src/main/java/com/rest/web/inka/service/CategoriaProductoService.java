package com.rest.web.inka.service;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rest.web.inka.dao.ICategoriaProductoDao;
import com.rest.web.inka.models.CategoriaProductos;
import com.rest.web.inka.models.CategoriaProductosDto;
import com.rest.web.inka.utilidades.PaginationMod;

@Service
public class CategoriaProductoService implements ICategoriaProductoService{

	@Autowired
	private ICategoriaProductoDao categoriaDao;

	@Autowired
	private ModelMapper mapper;
	
	@Override
	public CategoriaProductos buscarIdProducto(Integer id) {
		return categoriaDao.findById(id).orElse(null);
	}

	@Override
	public PaginationMod<CategoriaProductosDto> listarCategoriaProductoDtoPaginado(String nombre, Pageable pageable) {
		Page<CategoriaProductos> paginacion = categoriaDao.findByNombreContaining(nombre, pageable);
		
		PaginationMod<CategoriaProductosDto> paginationMod = new PaginationMod<CategoriaProductosDto>();
		CategoriaProductosDto[] entityDtos = mapper.map(paginacion.getContent(),CategoriaProductosDto[].class);
		paginationMod.setValue(paginacion, Arrays.asList(entityDtos));
		return paginationMod;
	}
	
	@Override
	public boolean buscarPorUrl(String url) {
		if(categoriaDao.existsByUrl(url)) {
			return false;
		}else {
			return true;
		}
	}

	@Override
	public CategoriaProductos guardar(CategoriaProductos categoria) {
		return categoriaDao.save(categoria);
	}

	@Override
	public CategoriaProductos buscarPorId(Integer id) {
		return categoriaDao.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Integer id) {
		categoriaDao.deleteById(id);
	}

	@Override
	public List<CategoriaProductos> listarCategoriaProducto() {
		return categoriaDao.findAll();
	}
	

}
