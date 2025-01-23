package com.example.snapcart_android_app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.CartItem
import com.example.domain.model.Order
import com.example.domain.network.ResultWrapper
import com.example.domain.usecase.*
import com.example.snapcart_android_app.ui.model.CartProduct
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

// Modify the ViewModel
class CartViewModel(
    private val addToCartUseCase: AddToCartUseCase,
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val createOrderUseCase: CreateOrderUseCase,
    private val clearCartUseCase: ClearCartUseCase
) : ViewModel() {

    private val _cartProducts = MutableStateFlow<List<CartProduct>>(emptyList())
    val cartProducts: StateFlow<List<CartProduct>> = _cartProducts

    private var currentUserId: String? = null

    fun loadCartItems(userId: String) {
        currentUserId = userId
        viewModelScope.launch {
            when (val result = getCartItemsUseCase.execute(userId)) {
                is ResultWrapper.Success -> {
                    val cartItems = result.value
                    val products = cartItems.map { cartItem ->
                        async {
                            getProductByIdUseCase.execute(cartItem.productId)
                        }
                    }.awaitAll()

                    _cartProducts.value =
                        cartItems.zip(products).mapNotNull { (item, productResult) ->
                            when (productResult) {
                                is ResultWrapper.Success -> CartProduct(item, productResult.value)
                                else -> null
                            }
                        }
                }

                is ResultWrapper.Failure -> { /* Handle error */
                }
            }
        }
    }
    fun addToCart(item: CartItem) {
        viewModelScope.launch {
            addToCartUseCase.execute(item)
            loadCartItems(item.userId)
        }
    }

    fun removeItem(itemId: String) {
        viewModelScope.launch {
            removeFromCartUseCase.execute(itemId)
            currentUserId?.let { loadCartItems(it) }
        }
    }
    fun checkout(userId: String, totalPrice: Double) {
        viewModelScope.launch {
            val cartItems = _cartProducts.value.map { it.cartItem }
            val order = Order(
                id = UUID.randomUUID().toString(),
                userId = userId,
                items = cartItems,
                totalPrice = totalPrice
            )

            when (val result = createOrderUseCase.execute(order)) {
                is ResultWrapper.Success -> {
                    clearCartUseCase.execute(userId)
                    loadCartItems(userId) // Refresh cart
                }
                is ResultWrapper.Failure -> {
                    // Handle error (e.g., show snackbar)
                }
            }
        }
    }
}