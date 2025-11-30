package com.example.level_up.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.level_up.data.model.Pedido
import com.example.level_up.data.model.PedidoConItems
import com.example.level_up.data.model.Producto
import com.example.level_up.data.repository.PedidoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PedidoViewModel(
    private val repository: PedidoRepository
) : ViewModel() {

    private val _pedidos = MutableStateFlow<List<PedidoConItems>>(emptyList())
    val pedidos: StateFlow<List<PedidoConItems>> = _pedidos.asStateFlow()

    init {
        // Escucha en vivo los cambios de Room
        viewModelScope.launch {
            repository.observarPedidos().collect { lista ->
                _pedidos.value = lista
            }
        }
    }

    fun crearPedido(productosCarrito: List<Producto>, direccion: String) {
        viewModelScope.launch {
            repository.crearPedido(productosCarrito, direccion)
        }
    }

    fun avanzarEstado(pedido: Pedido) {
        viewModelScope.launch {
            repository.avanzarEstado(pedido)
        }
    }
}
