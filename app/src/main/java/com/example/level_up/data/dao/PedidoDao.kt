package com.example.level_up.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.level_up.data.model.Pedido
import com.example.level_up.data.model.PedidoConItems
import com.example.level_up.data.model.PedidoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface PedidoDao {

    @Insert
    suspend fun insertPedido(pedido: Pedido): Long

    @Insert
    suspend fun insertItems(items: List<PedidoItem>)

    @Transaction
    @Query("SELECT * FROM pedidos ORDER BY fechaMillis DESC")
    fun getPedidosConItems(): Flow<List<PedidoConItems>>

    @Update
    suspend fun updatePedido(pedido: Pedido)
}
