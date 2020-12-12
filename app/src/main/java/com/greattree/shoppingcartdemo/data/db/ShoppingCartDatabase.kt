package com.greattree.shoppingcartdemo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.greattree.shoppingcartdemo.data.db.entity.Product

@Database(
    entities = [Product::class],
    version = 1,
    exportSchema = false
)

abstract class ShoppingCartDatabase: RoomDatabase() {
    abstract fun getShoppingCartDao(): ShoppingCartDao

    companion object {
        private const val DB_NAME = "product_database.db"
        @Volatile private var instance: ShoppingCartDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ShoppingCartDatabase::class.java,
            DB_NAME
        ).build()
    }
}