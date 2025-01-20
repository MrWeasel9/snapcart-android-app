package com.example.snapcart_android_app.ui.feature.profile

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.snapcart_android_app.AuthActivity
import com.example.snapcart_android_app.ui.UserViewModel

@Composable
fun ProfileScreen(navController: NavController) {
    val userViewModel: UserViewModel = viewModel()
    val context = LocalContext.current
    val userState = userViewModel.userState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (val state = userState.value) {
            is UserViewModel.UserState.Loading -> {
                Text("Loading...")
            }
            is UserViewModel.UserState.Unauthenticated -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "No user is currently authenticated,\nplease sign in here:",
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )

                    Button(
                        onClick = {
                            val intent = Intent(context, AuthActivity::class.java)
                            context.startActivity(intent)
                            (context as? Activity)?.finish()
                        },
                        modifier = Modifier.width(150.dp)
                    ) {
                        Text("Sign In")
                    }
                }
            }
            is UserViewModel.UserState.Authenticated -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("Email: ${state.user.email}")
                    Button(
                        onClick = {
                            userViewModel.logout()
                            val intent = Intent(context, AuthActivity::class.java)
                            context.startActivity(intent)
                            (context as? Activity)?.finish()
                        }
                    ) {
                        Text("Logout")
                    }
                }
            }
        }
    }
}