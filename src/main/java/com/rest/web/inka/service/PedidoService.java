package com.rest.web.inka.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rest.web.inka.dao.IPedidoDao;
import com.rest.web.inka.models.Almacen;

@Service
public class PedidoService  implements IPedidoService{
	
	private IPedidoDao pedidoDao;
	
	public PedidoService(IPedidoDao pedidoDao) {
		this.pedidoDao = pedidoDao;
	}
	@Override
	public List<Almacen> listarPedidos() {
		return pedidoDao.findAll();
	}

	@Override
	public Almacen buscarIdPedido(Integer id) {
		return pedidoDao.findById(id).orElse(null);
	}

	@Override
	public Almacen guardar(Almacen pedido) {
		return pedidoDao.save(pedido);
	}

	@Override
	public void eliminar(Integer id) {
		pedidoDao.deleteById(id);
	}

}
