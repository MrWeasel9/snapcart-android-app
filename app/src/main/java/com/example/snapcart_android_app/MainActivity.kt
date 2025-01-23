package com.example.snapcart_android_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.snapcart_android_app.ui.feature.home.HomeScreen
import com.example.snapcart_android_app.ui.theme.ShopperTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.runtime.getValue // Fix delegate error
import androidx.compose.runtime.remember // For SnackbarHostState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.domain.model.NetworkState
import com.example.domain.repository.NetworkStatusProvider
import com.example.snapcart_android_app.ui.feature.cart.CartScreen
import com.example.snapcart_android_app.ui.feature.detail.ProductDetailScreen
import com.example.snapcart_android_app.ui.feature.profile.ProfileScreen
import com.google.firebase.auth.FirebaseAuth
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopperTheme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }
                val networkStatusProvider: NetworkStatusProvider = koinInject()
                val networkState by networkStatusProvider.networkState.collectAsState(
                    initial = NetworkState.Connected // Add initial value
                )

                LaunchedEffect(networkState) {
                    if (networkState is NetworkState.Disconnected) {
                        snackbarHostState.showSnackbar("No internet connection")
                    }
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar(navController)
                    },
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        NavHost(navController = navController, startDestination = "home") {
                            composable("home") {
                                HomeScreen(navController)
                            }
                            composable("favorites") {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    Text(text = "Favorites")
                                }
                            }
                            composable("cart") {
                                CartScreen(navController)
                            }
                            composable("profile") {
                                ProfileScreen(navController)
                            }
                            // Replace the existing composable for product_detail
                            composable(
                                "product_detail/{productId}",
                                arguments = listOf(navArgument("productId") { type = NavType.LongType })
                            ) { backStackEntry ->
                                val productId = backStackEntry.arguments?.getLong("productId")
                                ProductDetailScreen(productId = productId) // No need to pass ViewModel manually
                            }

                        }
                    }
                }

            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        //current route
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        val items = listOf(
            BottomNavItems.Home,
            BottomNavItems.Cart,
            BottomNavItems.Profile
        )

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { startRoute ->
                            popUpTo(startRoute) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = { Text(text = item.title) },
                icon = {
                    Image(
                        painter = painterResource(id = item.icon),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(
                            if(currentRoute==item.route)
                                MaterialTheme.colorScheme.primary
                            else
                                Color.Gray)
                    )
                }, colors = NavigationBarItemDefaults.colors().copy(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = Color.Gray,
                    unselectedIconColor = Color.Gray
                )
            )
        }
    }
}

sealed class BottomNavItems(val route: String, val title: String, val icon: Int) {
    object Home : BottomNavItems("home", "Home", icon = R.drawable.ic_home)
    object Cart : BottomNavItems("cart", "Cart", icon = R.drawable.ic_cart)
    object Profile : BottomNavItems("profile", "Profile", icon = R.drawable.ic_profile_bn)
}