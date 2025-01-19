// app/AuthActivity.kt
package com.example.snapcart_android_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.domain.repository.AuthRepository
import com.example.data.repository.AuthRepositoryImplementation


class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create an instance of the repository implementation
        val authRepository: AuthRepository = AuthRepositoryImplementation()

        setContent {
            AuthNavigation(authRepository)
        }
    }
}

@Composable
fun AuthNavigation(authRepository: AuthRepository) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "signIn") {
        composable("signIn") {
            SignInScreen(navController, authRepository)
        }
        composable("signUp") {
            SignUpScreen(navController, authRepository)
        }
    }
}