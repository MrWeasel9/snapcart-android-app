package com.example.snapcart_android_app.ui.feature.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductDetailScreen(productId: Long?) {
    val viewModel: ProductDetailViewModel = koinViewModel()
    val productState by viewModel.uiState.collectAsState()

    LaunchedEffect(productId) {
        productId?.let { viewModel.getProductById(it) }
    }

    when (productState) {
        is ProductDetailUIState.Loading -> Text("Loading...")
        is ProductDetailUIState.Success -> {
            val product = (productState as ProductDetailUIState.Success).product
            Text(text = product.title)
        }
        is ProductDetailUIState.Error -> Text("Error fetching product")
    }
}