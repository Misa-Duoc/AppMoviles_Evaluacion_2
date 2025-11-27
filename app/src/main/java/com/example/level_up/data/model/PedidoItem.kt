package com.example.level_up.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pedido_items")
data class PedidoItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val pedidoId: Long,
    val productoId: Int,
    val nombre: String,
    val precioUnitario: Double,
    val cantidad: Int
)

