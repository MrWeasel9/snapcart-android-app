package com.example.snapcart_android_app.ui.feature.cart

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.snapcart_android_app.ui.model.CartViewModel
import com.example.snapcart_android_app.ui.model.UserViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.snapcart_android_app.utils.NotificationHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CartScreen() {

    val cartViewModel: CartViewModel = koinViewModel()
    val userViewModel: UserViewModel = viewModel()
    val userState by userViewModel.userState.collectAsState()
    val context = LocalContext.current
    val rawCartProducts by cartViewModel.cartProducts.collectAsState()


    // Create a mutable state list for animated removals
    val cartProducts = remember { mutableStateListOf(*rawCartProducts.toTypedArray()) }

    // Sync with ViewModel's StateFlow updates
    LaunchedEffect(rawCartProducts) {
        cartProducts.clear()
        cartProducts.addAll(rawCartProducts)
    }

    LaunchedEffect(userState) {
        if (userState is UserViewModel.UserState.Authenticated) {
            cartViewModel.loadCartItems((userState as UserViewModel.UserState.Authenticated).user.uid)
        }
    }

    LaunchedEffect(cartViewModel.checkoutState) {
        cartViewModel.checkoutState.collect { state ->
            when (state) {
                is CartViewModel.CheckoutState.Success -> {
                    NotificationHelper.showNotification(
                        context = context,
                        title = "Order Received",
                        message = "Order received, value: $${"%.2f".format(state.totalPrice)}"
                    )
                    cartViewModel.resetCheckoutState() // Reset state
                }
                else -> {}
            }
        }
    }

    val totalPrice = cartProducts.sumOf { it.product.price }

    Box(modifier = Modifier.fillMaxSize()) {
        if (cartProducts.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 96.dp)
            ) {
                items(
                    items = cartProducts,
                    key = { it.cartItem.id }
                ) { cartProduct ->
                    var isVisible by remember { mutableStateOf(true) }
                    val scope = rememberCoroutineScope()
                    AnimatedVisibility(
                        visible = isVisible,
                        exit = slideOutHorizontally(
                            animationSpec = tween(durationMillis = 300),
                            targetOffsetX = { fullWidth -> fullWidth } // Swipe to right
                        ) + fadeOut()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp, vertical = 8.dp),

                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${cartProducts.indexOf(cartProduct) + 1}.",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            AsyncImage(
                                model = cartProduct.product.image,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = cartProduct.product.title)
                                Text(text = cartProduct.product.priceString)
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            IconButton(
                                onClick = {
                                    isVisible = false
                                    scope.launch { // Use coroutine scope here
                                        delay(300)
                                        cartProducts.remove(cartProduct)
                                        cartViewModel.removeItem(cartProduct.cartItem.id)
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Remove item",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }

            // Sticky footer remains unchanged
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                tonalElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(72.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total: $${"%.2f".format(totalPrice)}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Button(
                        onClick = {
                            if (userState is UserViewModel.UserState.Authenticated) {
                                val userId = (userState as UserViewModel.UserState.Authenticated).user.uid
                                cartViewModel.checkout(userId, totalPrice)
                            }
                        }
                    ) {
                        Text("Checkout")
                    }
                }
            }
        } else {
            EmptyCartMessage()
        }
    }
}

@Composable
private fun EmptyCartMessage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🛒", // Shopping cart emoji
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Your cart is currently empty",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Won't you add something already? :)",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}