package com.example.level_up.viewmodel

import androidx.lifecycle.ViewModel
import com.example.level_up.data.model.Pedido
import com.example.level_up.data.model.PedidoConItems
import com.example.level_up.data.model.PedidoEstado
import com.example.level_up.data.model.PedidoItem
import com.example.level_up.data.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PedidoViewModel : ViewModel() {

    private val _pedidos = MutableStateFlow<List<PedidoConItems>>(emptyList())
    val pedidos: StateFlow<List<PedidoConItems>> = _pedidos.asStateFlow()

    fun crearPedido(productosCarrito: List<Producto>, direccion: String) {
        // Producto.precio es String, se convierte a Double.
        val total = productosCarrito.sumOf { it.precio.toDoubleOrNull() ?: 0.0 }

        // Generar id local incremental (mientras no insertes en Room)
        val nuevoId = (_pedidos.value.maxOfOrNull { it.pedido.id } ?: 0L) + 1L

        val nuevoPedido = Pedido(
            id = nuevoId,
            fechaMillis = System.currentTimeMillis(),
            total = total,
            direccion = direccion,
            estado = PedidoEstado.PENDIENTE
        )

        // Mapear cada Producto
        val items: List<PedidoItem> = productosCarrito.map { producto ->
            PedidoItem(
                pedidoId = nuevoId,
                productoId = producto.id,                          // Int
                nombre = producto.nombre,
                precioUnitario = producto.precio.toDoubleOrNull() ?: 0.0,
                cantidad = producto.cantidad.toIntOrNull() ?: 1
            )
        }

        val pedidoConItems = PedidoConItems(
            pedido = nuevoPedido,
            items = items
        )

        _pedidos.value = _pedidos.value + pedidoConItems
    }

    fun avanzarEstado(pedido: Pedido) {
        val siguiente = when (pedido.estado) {
            PedidoEstado.PENDIENTE  -> PedidoEstado.PREPARANDO
            PedidoEstado.PREPARANDO -> PedidoEstado.EN_CAMINO
            PedidoEstado.EN_CAMINO  -> PedidoEstado.ENTREGADO
            PedidoEstado.ENTREGADO  -> PedidoEstado.ENTREGADO
        }

        _pedidos.value = _pedidos.value.map {
            if (it.pedido.id == pedido.id) {
                it.copy(pedido = it.pedido.copy(estado = siguiente))
            } else it
        }
    }
}
