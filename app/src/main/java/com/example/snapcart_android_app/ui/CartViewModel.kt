package com.example.snapcart_android_app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.CartItem
import com.example.domain.network.ResultWrapper
import com.example.domain.usecase.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val addToCartUseCase: AddToCartUseCase,
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase
) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    fun loadCartItems(userId: String) {

        viewModelScope.launch {
            when (val result = getCartItemsUseCase.execute(userId)) {
                is ResultWrapper.Success -> _cartItems.value = result.value
                is ResultWrapper.Failure -> {/* Handle error */}
            }
        }
    }

    fun addToCart(item: CartItem) {
        viewModelScope.launch {
            addToCartUseCase.execute(item)
            loadCartItems(item.userId)
        }
    }
}