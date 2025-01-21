package com.example.snapcart_android_app.ui.feature.cart

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import com.example.snapcart_android_app.AuthActivity
import com.example.snapcart_android_app.ui.CartViewModel
import com.example.snapcart_android_app.ui.UserViewModel
import org.koin.androidx.compose.koinViewModel

// Create CartScreen.kt
@Composable
fun CartScreen(navController: NavController) {
    val cartViewModel: CartViewModel = koinViewModel()
    val userViewModel: UserViewModel = viewModel()
    val cartItems by cartViewModel.cartItems.collectAsState()
    val userState by userViewModel.userState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(userState) {
        if (userState is UserViewModel.UserState.Authenticated) {
            cartViewModel.loadCartItems((userState as UserViewModel.UserState.Authenticated).user.uid)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (userState) {
            is UserViewModel.UserState.Authenticated -> {
                if (cartItems.isEmpty()) {
                    Text("Your cart is empty")
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(cartItems) { item ->
                            Text(
                                text = item.productName,
                                modifier = Modifier.padding(16.dp)
                            )
                            Text("Product ID: ${item.productId}")
                            Text("Added at: ${item.addedAt}")
                        }
                    }
                }
            }
            else -> {
                Text(
                    text = "You need to be authenticated to view your cart",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    onClick = {
                        // Launch the AuthActivity
                        context.startActivity(Intent(context, AuthActivity::class.java))
                    }
                ) {
                    Text("Sign in here")
                }
            }
        }
    }
}