package com.example.snapcart_android_app.ui.feature.cart

import android.content.Intent
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
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.snapcart_android_app.AuthActivity
import com.example.snapcart_android_app.ui.CartViewModel
import com.example.snapcart_android_app.ui.UserViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartScreen(navController: NavController) {
    val cartViewModel: CartViewModel = koinViewModel()
    val userViewModel: UserViewModel = viewModel()
    val userState by userViewModel.userState.collectAsState()
    val context = LocalContext.current
    val cartProducts by cartViewModel.cartProducts.collectAsState()
    val totalPrice = cartProducts.sumOf { it.product.price }

    LaunchedEffect(userState) {
        if (userState is UserViewModel.UserState.Authenticated) {
            cartViewModel.loadCartItems((userState as UserViewModel.UserState.Authenticated).user.uid)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 96.dp) // Space for sticky footer
        ) {
            itemsIndexed(cartProducts) { index, cartProduct ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${index + 1}.",
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
                        onClick = { cartViewModel.removeItem(cartProduct.cartItem.id) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Remove item"
                        )
                    }
                }
            }
        }

        // Sticky footer with total and checkout button
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
                    onClick = { /* TODO: Checkout logic */ }
                ) {
                    Text("Checkout")
                }
            }
        }
    }
}