package com.example.level_up.data.repository

import com.example.level_up.data.dao.PedidoDao
import com.example.level_up.data.model.*
import kotlinx.coroutines.flow.Flow

class PedidoRepository(private val dao: PedidoDao) {
/*
    fun observarPedidos(): Flow<List<PedidoConItems>> = dao.getPedidosConItems()
    fun getPedidos(): Flow<List<PedidoConItems>> = dao.getPedidos()

    suspend fun crearPedido(productos: List<Producto>, direccion: String) {
        // Producto.precio y cantidad son String: convertir
        val total = productosCarrito.sumOf { it.precio.toDoubleOrNull() ?: 0.0 }

        val pedidoId = dao.insertPedido(
            Pedido(
                fechaMillis = System.currentTimeMillis(),
                total = total,
                direccion = direccion
            )
        )

        val items = productosCarrito.map {
            PedidoItem(
                pedidoId = pedidoId,
                productoId = it.id,
                nombre = it.nombre,
                precioUnitario = it.precio.toDoubleOrNull() ?: 0.0,
                cantidad = it.cantidad.toIntOrNull() ?: 1
            )
        }
        dao.insertItems(items)
    }

    suspend fun cambiarEstado(pedido: Pedido, nuevoEstado: PedidoEstado) {
        dao.updatePedido(pedido.copy(estado = nuevoEstado))
    }

 */
}
