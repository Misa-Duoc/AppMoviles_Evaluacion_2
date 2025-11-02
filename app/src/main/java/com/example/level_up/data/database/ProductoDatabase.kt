package com.example.level_up.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.level_up.data.dao.ProductoDao
import com.example.level_up.data.model.Producto

@Database(
    entities = [Producto::class],
    version=1,
    exportSchema = false // evite warning
)

abstract class ProductoDatabase: RoomDatabase(){
    abstract fun productoDao(): ProductoDao

    companion object{
        @Volatile   //  cualquier cambio en el valor de la INSTANCE será visible
        private var INSTANCE:ProductoDatabase? = null

        fun getDatabase(context: Context):ProductoDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductoDatabase::class.java,
                    "producto_database"
                ).build()
                INSTANCE = instance
                instance

            } // fin return

        }// fin getdatabase

    } // fin companion

} // fin abstract
