package com.example.snapcart_android_app.ui.feature.profile

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.snapcart_android_app.ui.UserViewModel

@Composable
fun ProfileScreen(navController: NavController) {
    val userViewModel: UserViewModel = viewModel()

    val userState = userViewModel.userState.collectAsState()

    when (val state = userState.value) {
        is UserViewModel.UserState.Loading -> {
            // Show loading indicator
            Text("Loading...")
        }
        is UserViewModel.UserState.Unauthenticated -> {
            // Handle unauthenticated user
            Button(onClick = { navController.navigate("signIn") }) {
                Text("Sign In")
            }
        }
        is UserViewModel.UserState.Authenticated -> {
            // Display user data
            Row {
                Text("Email: ${state.user.email}")
                Text("Display Name: ${state.user.displayName}")
                // Add more user details as needed
            }
        }
    }
}