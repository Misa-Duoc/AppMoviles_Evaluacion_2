package com.example.level_up.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.level_up.data.model.Producto
import com.example.level_up.data.repository.ProductoRepository
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
        // Escucha continuamente los cambios en la tabla "productos"
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
            if (nuevaCantidad <= 0) {
                repository.eliminarProducto(producto)
            } else {
                val totalActual = producto.precio.toIntOrNull() ?: 0
                val cantidadActual = producto.cantidad.toIntOrNull() ?: 1
                val unitPrice =
                    if (cantidadActual > 0) totalActual / cantidadActual else totalActual
                val nuevoTotal = unitPrice * nuevaCantidad

                val actualizado = producto.copy(
                    cantidad = nuevaCantidad.toString(),
                    precio = nuevoTotal.toString()
                )

                repository.actualizarProducto(actualizado)
            }
        }
    }
}
