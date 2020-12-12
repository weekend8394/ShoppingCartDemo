package com.greattree.shoppingcartdemo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.greattree.shoppingcartdemo.repository.ShoppingCartRepository

class ShoppingCartViewModelFactory(
    private val repository: ShoppingCartRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            val constructor = modelClass.getDeclaredConstructor(ShoppingCartRepository::class.java)
            return constructor.newInstance(repository)
        } catch (e: Exception) {

        }
        return super.create(modelClass)
    }
}