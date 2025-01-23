package com.example.snapcart_android_app.ui.feature.detail

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.domain.model.CartItem
import com.example.snapcart_android_app.ui.CartViewModel
import com.example.snapcart_android_app.ui.UserViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ProductDetailScreen(productId: Long?) {
    val viewModel: ProductDetailViewModel = koinViewModel()
    val productState by viewModel.uiState.collectAsState()
    val isFavorite = remember { mutableStateOf(false) }
    val userViewModel: UserViewModel = koinViewModel()
    val cartViewModel: CartViewModel = koinViewModel()
    val userState by userViewModel.userState.collectAsState()

    // Animation states
    var cartIconPosition by remember { mutableStateOf(Offset.Zero) }
    var productImagePosition by remember { mutableStateOf(Offset.Zero) }
    var isAnimating by remember { mutableStateOf(false) }
    val animationProgress = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(productId) {
        productId?.let { viewModel.getProductById(it) }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        Scaffold { paddingValues ->
            // Flying image overlay
            if (isAnimating && productState is ProductDetailUIState.Success) {
                val product = (productState as ProductDetailUIState.Success).product
                val x = (cartIconPosition.x - productImagePosition.x) * animationProgress.value + productImagePosition.x
                val y = (cartIconPosition.y - productImagePosition.y) * animationProgress.value + productImagePosition.y
                val scale = 1f - (animationProgress.value * 0.6f) // Reduced scaling (40% instead of 20%)


                AsyncImage(
                    model = product.image,
                    contentDescription = null,
                    modifier = Modifier
                        .graphicsLayer {
                            translationX = x
                            translationY = y
                            scaleX = scale
                            scaleY = scale
                            alpha = 1f

                        }
                        .size(120.dp), // Increased size
                    contentScale = ContentScale.Fit
                )
            }

            when (productState) {
                is ProductDetailUIState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is ProductDetailUIState.Success -> {
                    val product = (productState as ProductDetailUIState.Success).product
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .verticalScroll(rememberScrollState())
                    ) {
                        // Image Container
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .background(Color.Black)
                                .onGloballyPositioned {
                                    productImagePosition = it.boundsInWindow().center
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = product.image,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 300.dp),
                                contentScale = ContentScale.Fit
                            )
                        }

                        // Product Details
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text(
                                text = product.title,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "$${product.price}",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = product.description,
                                style = MaterialTheme.typography.bodyLarge,
                                maxLines = 10,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        // Action Buttons
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(
                                onClick = { isFavorite.value = !isFavorite.value },
                                modifier = Modifier.size(48.dp)
                            ) {
                                Icon(
                                    imageVector = if (isFavorite.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    tint = if (isFavorite.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                )
                            }

                            Button(
                                onClick = {
                                    when (val state = userState) {
                                        is UserViewModel.UserState.Authenticated -> {
                                            isAnimating = true
                                            scope.launch {
                                                animationProgress.snapTo(0f)
                                                animationProgress.animateTo(
                                                    targetValue = 1f,
                                                    animationSpec = tween(
                                                        durationMillis = 650, // Faster animation
                                                        easing = FastOutSlowInEasing // Better easing curve
                                                    )
                                                )
                                                cartViewModel.addToCart(
                                                    CartItem(
                                                        userId = state.user.uid,
                                                        productId = product.id,
                                                        productName = product.title
                                                    )
                                                )
                                                isAnimating = false
                                            }
                                        }
                                        else -> {
                                            // Show login prompt
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .weight(0.7f)
                                    .onGloballyPositioned {
                                        cartIconPosition = it.boundsInWindow().center
                                    }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ShoppingCart,
                                    contentDescription = "Add to Cart"
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                                Text("Add to Cart")
                            }
                        }
                    }
                }

                is ProductDetailUIState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Error fetching product details")
                    }
                }
            }
        }
    }
}