package com.greattree.shoppingcartdemo.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.greattree.shoppingcartdemo.data.db.entity.Product

@Dao
interface ShoppingCartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) //if some data is same/conflict, it'll be replace with new data.
    suspend fun insertProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("SELECT * FROM product ORDER BY priority DESC")
    fun getAllNotes(): LiveData<List<Product>>
    // why not use suspend ? because Room does not support LiveData with suspended functions.
    // LiveData already works on a background thread and should be used directly without using coroutines

    @Query("DELETE FROM product")
    suspend fun clearNote()

    @Query("DELETE FROM product WHERE id = :id") //you can use this too, for delete note by id.
    suspend fun deleteNoteById(id: Int)
}