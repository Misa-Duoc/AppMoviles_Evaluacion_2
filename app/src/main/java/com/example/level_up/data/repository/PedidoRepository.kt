package com.example.level_up.data.repository

import com.example.level_up.data.dao.PedidoDao
import com.example.level_up.data.model.Pedido
import com.example.level_up.data.model.PedidoConItems
import com.example.level_up.data.model.PedidoEstado
import com.example.level_up.data.model.PedidoItem
import com.example.level_up.data.model.Producto
import kotlinx.coroutines.flow.Flow

class PedidoRepository(private val dao: PedidoDao) {

    fun observarPedidos(): Flow<List<PedidoConItems>> = dao.getPedidosConItems()

    suspend fun crearPedido(productos: List<Producto>, direccion: String) {
        val total = productos.sumOf { it.precio.toDoubleOrNull() ?: 0.0 }

        val pedido = Pedido(
            fechaMillis = System.currentTimeMillis(),
            total = total,
            direccion = direccion,
            estado = PedidoEstado.PENDIENTE      // 👈 enum directo
        )

        // ID autogenerado del pedido
        val pedidoId = dao.insertPedido(pedido)

        val items = productos.map { p ->
            PedidoItem(
                pedidoId = pedidoId,
                productoId = 0L,                  // 👈 placeholder, si no tienes ID de producto
                nombre = p.nombre,
                precioUnitario = p.precio.toDoubleOrNull() ?: 0.0,
                cantidad = p.cantidad.toIntOrNull() ?: 1
            )
        }

        dao.insertItems(items)
    }

    suspend fun avanzarEstado(pedido: Pedido) {
        val siguiente = when (pedido.estado) {
            PedidoEstado.PENDIENTE   -> PedidoEstado.PREPARANDO
            PedidoEstado.PREPARANDO  -> PedidoEstado.EN_CAMINO
            PedidoEstado.EN_CAMINO   -> PedidoEstado.ENTREGADO
            PedidoEstado.ENTREGADO   -> PedidoEstado.ENTREGADO
        }

        dao.updatePedido(pedido.copy(estado = siguiente))
    }
}
