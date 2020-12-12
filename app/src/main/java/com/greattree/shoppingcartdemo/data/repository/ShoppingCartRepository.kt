package com.greattree.shoppingcartdemo.data.repository

import androidx.lifecycle.LiveData
import com.greattree.shoppingcartdemo.data.db.ShoppingCartDatabase
import com.greattree.shoppingcartdemo.data.db.entity.Product

class ShoppingCartRepository(
    private val shoppingCartDatabase: ShoppingCartDatabase
) {
    suspend fun insertNote(product: Product) = shoppingCartDatabase.getShoppingCartDao().insertProduct(product)

    suspend fun updateNote(product: Product) = shoppingCartDatabase.getShoppingCartDao().updateProduct(product)

    suspend fun deleteNote(product: Product) = shoppingCartDatabase.getShoppingCartDao().deleteProduct(product)

    suspend fun deleteNoteById(id: Int) = shoppingCartDatabase.getShoppingCartDao().deleteNoteById(id)

    suspend fun clearNote() = shoppingCartDatabase.getShoppingCartDao().clearNote()

    fun getAllNotes(): LiveData<List<Product>> = shoppingCartDatabase.getShoppingCartDao().getAllNotes()
}