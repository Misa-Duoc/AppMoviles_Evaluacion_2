package com.example.level_up.viewmodel

import ProductoRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.level_up.data.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductoViewModel(
    private val repository: ProductoRepository
) : ViewModel() {

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    init {
        // Escucha en vivo los productos desde Room
        viewModelScope.launch {
            repository.obtenerProductos().collect { lista ->
                _productos.value = lista
            }
        }
    }

    fun guardarProducto(producto: Producto) {
        viewModelScope.launch {
            repository.insertarProducto(producto)
        }
    }

    fun eliminarProducto(producto: Producto) {
        viewModelScope.launch {
            repository.eliminarProducto(producto)
        }
    }

    fun actualizarCantidad(producto: Producto, nuevaCantidad: Int) {
        viewModelScope.launch {
            // Si la cantidad es 0 o menos, eliminamos el producto del carrito
            if (nuevaCantidad <= 0) {
                repository.eliminarProducto(producto)
                return@launch
            }

            // Calculamos precio unitario y nuevo total
            val totalActual = producto.precio.toIntOrNull() ?: 0
            val cantidadActual = producto.cantidad.toIntOrNull() ?: 1
            val unitPrice =
                if (cantidadActual > 0) totalActual / cantidadActual else totalActual
            val nuevoTotal = unitPrice * nuevaCantidad

            val actualizado = producto.copy(
                cantidad = nuevaCantidad.toString(),
                precio = nuevoTotal.toString()
            )

            // Guardamos el producto actualizado en Room
            repository.actualizarProducto(actualizado)
        }
    }
}
