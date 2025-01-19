package com.example.snapcart_android_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Window appearance settings
        window.apply {
            setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        setContent {
            IntroScreen(
                onClick = {
                    startActivity(Intent(this, MainActivity::class.java))
                },
                onContactClick = {
                    // Use replace instead of add
                    supportFragmentManager.beginTransaction()
                        .replace(android.R.id.content, ContactFragment())
                        .addToBackStack(null)
                        .commit()
                },
                onSignInClick = {
                    startActivity(Intent(this, AuthActivity::class.java))
                }
            )
        }
    }
}

@Composable
@Preview
fun IntroScreen(
    onClick: () -> Unit = {},
    onContactClick: () -> Unit = {},
    onSignInClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.intro_logo),
            contentDescription = "Intro Logo",
            modifier = Modifier
                .padding(top = 48.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Welcome to Snapcart!\n " +
                    "The easiest way to manage your shopping.",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 32.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onClick() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.purple_500)
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "Let's Get Started!",
                color = Color.White,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onContactClick() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "Contact",
                color = Color.Black,
                fontSize = 16.sp
            )
        }

        TextButton(
            onClick = { onSignInClick() },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "Sign in Here",
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
        }
    }
}

