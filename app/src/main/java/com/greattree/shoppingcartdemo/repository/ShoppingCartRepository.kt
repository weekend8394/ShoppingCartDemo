package com.greattree.shoppingcartdemo.repository

import androidx.lifecycle.LiveData
import com.greattree.shoppingcartdemo.data.db.ShoppingCartDatabase
import com.greattree.shoppingcartdemo.data.db.entity.Product

class ShoppingCartRepository(
    private val shoppingCartDatabase: ShoppingCartDatabase
) {

    suspend fun insertProduct(product: Product) = shoppingCartDatabase.getShoppingCartDao().insertProduct(product)

    suspend fun updateProduct(product: Product) = shoppingCartDatabase.getShoppingCartDao().updateProduct(product)

    suspend fun deleteProduct(product: Product) = shoppingCartDatabase.getShoppingCartDao().deleteProduct(product)

    suspend fun deleteProductById(id: Int) = shoppingCartDatabase.getShoppingCartDao().deleteNoteById(id)

    suspend fun clearProduct() = shoppingCartDatabase.getShoppingCartDao().clearNote()

    fun getAllProducts(): LiveData<List<Product>> = shoppingCartDatabase.getShoppingCartDao().getAllNotes()
}