package com.example.level_up.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.level_up.data.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductoViewModel : ViewModel() {

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    fun guardarProducto(producto: Producto) {
        viewModelScope.launch {
            val nuevaLista = _productos.value + producto
            _productos.value = nuevaLista
        }
    }

    fun eliminarProducto(producto: Producto) {
        viewModelScope.launch {
            _productos.value = _productos.value.filterNot { it == producto }
        }
    }

    fun actualizarCantidad(producto: Producto, nuevaCantidad: Int) {
        if (nuevaCantidad <= 0) {
            eliminarProducto(producto)
            return
        }

        viewModelScope.launch {
            val listaActual = _productos.value
            val actualizada = listaActual.map { p ->
                if (p == producto) {
                    val totalActual = p.precio.toIntOrNull() ?: 0
                    val cantidadActual = p.cantidad.toIntOrNull() ?: 1
                    val unitPrice =
                        if (cantidadActual > 0) totalActual / cantidadActual else totalActual
                    val nuevoTotal = unitPrice * nuevaCantidad

                    p.copy(
                        cantidad = nuevaCantidad.toString(),
                        precio = nuevoTotal.toString()
                    )
                } else {
                    p
                }
            }
            _productos.value = actualizada
        }
    }
}
