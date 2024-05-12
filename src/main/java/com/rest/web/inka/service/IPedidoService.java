package com.rest.web.inka.service;

import java.util.List;

import com.rest.web.inka.models.Almacen;


public interface IPedidoService {
	List<Almacen> listarPedidos();
	
	Almacen buscarIdPedido(Integer id);
	
	Almacen guardar(Almacen pedido);
	
	void eliminar(Integer id);
}
