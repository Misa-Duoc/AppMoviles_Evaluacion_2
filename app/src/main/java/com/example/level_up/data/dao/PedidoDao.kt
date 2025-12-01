package com.example.level_up.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.level_up.data.model.Pedido
import com.example.level_up.data.model.PedidoConItems
import com.example.level_up.data.model.PedidoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface PedidoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPedido(pedido: Pedido): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<PedidoItem>)

    @Update
    suspend fun updatePedido(pedido: Pedido)

    @Transaction
    @Query("SELECT * FROM pedidos")
    fun getPedidosConItems(): Flow<List<PedidoConItems>>
}
