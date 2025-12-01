package com.example.level_up.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pedidos")
data class Pedido(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val total: Double,
    val direccion: String?,
    val estado: PedidoEstado   // 👈 IMPORTANTE: enum, no String
)
