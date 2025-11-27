package com.example.level_up.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class PedidoConItems(
    @Embedded val pedido: Pedido,
    @Relation(
        parentColumn = "id",
        entityColumn = "pedidoId"
    )
    val items: List<PedidoItem>
)
