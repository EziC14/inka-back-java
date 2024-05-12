package com.rest.web.inka.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.web.inka.models.Almacen;
import com.rest.web.inka.models.ItemAlmacen;
import com.rest.web.inka.service.IPedidoService;
import com.rest.web.inka.utilidades.Utilidades;


@RestController
@RequestMapping("/api/almacen")
public class ApiAlmacen {
	@Autowired
	private IPedidoService pedidoService;
	
	
	@GetMapping("/listar")
	public List<Almacen> listar() {
		return pedidoService.listarPedidos();
	}
	@GetMapping("/listar/{id}")
	public Almacen listarPedidoId(@PathVariable Integer id) {
		return pedidoService.buscarIdPedido(id);
	}
	
	@PostMapping("/guardar-pedidos")
	public ResponseEntity<Object> guardarPedido(@RequestBody Almacen almacen) {				
		Almacen almacenNuevo = new Almacen();
		
		almacenNuevo.setDescripcion(almacen.getDescripcion());
		almacenNuevo.setObservacion(almacen.getObservacion());
		almacenNuevo.setFecha(almacen.getFecha());
		almacenNuevo.setUsuario(almacen.getUsuario());
		almacenNuevo.setUsuarioCliente(almacen.getUsuarioCliente());		
		for (int i = 0; i < almacen.getItemsAlmacen().size(); i++) {
			ItemAlmacen itemAlmacen = new ItemAlmacen();
			itemAlmacen.setCantidad(almacen.getItemsAlmacen().get(i).getCantidad());
			itemAlmacen.setProducto(almacen.getItemsAlmacen().get(i).getProducto());
			itemAlmacen.setPedido(almacenNuevo);
			almacenNuevo.addItemPedido(itemAlmacen);
			
		}
		pedidoService.guardar(almacenNuevo);
		
		return Utilidades.generateResponseTrue(HttpStatus.CREATED, "PEDIDO CREADO CORRECTAMENTE");
	}
	
	@PostMapping("/eliminar")
	public ResponseEntity<Object> eliminarPedidoId(@RequestBody Almacen almacen) {
		
		Almacen buscarId = pedidoService.buscarIdPedido(almacen.getId());
		
		if (buscarId == null) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL PEDIDO");
		}
		
		pedidoService.eliminar(almacen.getId());
		return Utilidades.generateResponseTrue(HttpStatus.OK, "PEDIDO ELIMINADO CORRECTAMENTE");
	}
}
