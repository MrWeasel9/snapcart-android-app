package com.example.snapcart_android_app

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.domain.repository.AuthRepository

@Composable
fun SignUpScreen(
    navController: NavController,
    authRepository: AuthRepository
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (password == confirmPassword) {
                    authRepository.signUpWithEmailAndPassword(
                        email = email,
                        password = password,
                        onSuccess = {
                            // Navigate to MainActivity on success
                            val intent = Intent(context, MainActivity::class.java)
                            context.startActivity(intent)
                            (context as? Activity)?.finish()
                        },
                        onError = { message ->
                            errorMessage = message
                        }
                    )
                } else {
                    errorMessage = "Passwords do not match"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Up")
        }

        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = {
                navController.navigate("signIn")
            }
        ) {
            Text("Already have an account? Sign in here", color = Color.Blue)
        }
    }
}