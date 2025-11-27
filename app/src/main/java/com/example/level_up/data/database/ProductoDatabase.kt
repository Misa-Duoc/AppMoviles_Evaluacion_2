package com.example.level_up.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.level_up.data.dao.ProductoDao
import com.example.level_up.data.dao.PedidoDao
import com.example.level_up.data.model.Producto
import com.example.level_up.data.model.Pedido
import com.example.level_up.data.model.PedidoItem

@Database(
    entities = [
        Producto::class,
        Pedido::class,
        PedidoItem::class
    ],
    version = 2,
    exportSchema = false
)
abstract class ProductoDatabase : RoomDatabase() {

    abstract fun productoDao(): ProductoDao
    abstract fun pedidoDao(): PedidoDao

    companion object {
        @Volatile
        private var INSTANCE: ProductoDatabase? = null

        fun getDatabase(context: Context): ProductoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductoDatabase::class.java,
                    "producto_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
