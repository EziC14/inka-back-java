package com.rest.web.inka.service;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rest.web.inka.dao.IProductoDao;
import com.rest.web.inka.models.Producto;
import com.rest.web.inka.models.ProductoDto;
import com.rest.web.inka.utilidades.PaginationMod;

@Service
public class ProductoService implements IProductoService{

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private IProductoDao productoDao;
	
	@Override
	public PaginationMod<ProductoDto> listarProductoDtoPaginado(String nombre, Pageable pageable) {
		Page<Producto> paginacion = productoDao.findByNombreContaining(nombre, pageable);
		
		PaginationMod<ProductoDto> paginationMod = new PaginationMod<ProductoDto>();
		ProductoDto[] entityDtos = mapper.map(paginacion.getContent(),ProductoDto[].class);
		paginationMod.setValue(paginacion, Arrays.asList(entityDtos));
		return paginationMod;
	}
	
	@Override
	public PaginationMod<ProductoDto> buscarProductoPorNombrePaginado(String nombre, Pageable pageable) {
		Page<Producto> paginacion = productoDao.findByNombreContaining(nombre, pageable);
		
		PaginationMod<ProductoDto> paginationMod = new PaginationMod<>();
		List<ProductoDto> productoDtos = paginacion.getContent().stream()
			.map(producto -> mapper.map(producto, ProductoDto.class))
			.collect(Collectors.toList());
		
		paginationMod.setValue(paginacion, productoDtos);
		return paginationMod;
	}

	@Override
	public PaginationMod<ProductoDto> listarProductosBajoStock(Integer stockMinimo, Pageable pageable) {
		Page<Producto> paginacion = productoDao.findByStockLessThan(stockMinimo, pageable);
		
		PaginationMod<ProductoDto> paginationMod = new PaginationMod<>();
		List<ProductoDto> productoDtos = paginacion.getContent().stream()
			.map(producto -> mapper.map(producto, ProductoDto.class))
			.collect(Collectors.toList());
		
		paginationMod.setValue(paginacion, productoDtos);
		return paginationMod;
	}
	
	public List<Producto> list() {
        return productoDao.findAll();
    }

	@Override
	public Producto buscarIdProducto(Integer id) {
		return productoDao.findById(id).orElse(null);
	}
	
	@Override
	public Producto guardar(Producto producto) {
		return productoDao.save(producto);
	}

	@Override
	public void eliminar(Integer id) {
		productoDao.deleteById(id);	
	}

}
