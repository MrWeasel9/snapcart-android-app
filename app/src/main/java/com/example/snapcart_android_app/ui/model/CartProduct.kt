// ui/model/CartProduct.kt
package com.example.snapcart_android_app.ui.model

import com.example.domain.model.CartItem
import com.example.domain.model.Product

data class CartProduct(
    val cartItem: CartItem,
    val product: Product
)