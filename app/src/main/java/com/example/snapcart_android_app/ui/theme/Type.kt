package com.example.snapcart_android_app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.snapcart_android_app.R


val googleFontFamily = FontFamily(
    Font(R.font.googlesans_regular, FontWeight.Normal),
    Font(R.font.googlesans_bold, FontWeight.Bold),
    Font(R.font.googlesans_medium, FontWeight.Medium),
    Font(R.font.googlesans_italic, FontWeight.Light),
    Font(R.font.googlesans_mediumitalic, FontWeight.Medium),
    Font(R.font.googlesans_bolditalic, FontWeight.Bold)

)

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = googleFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp
    ),
    displayMedium = TextStyle(
        fontFamily = googleFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp
    ),
    displaySmall = TextStyle(
        fontFamily = googleFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 30.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = googleFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = googleFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = googleFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    titleLarge = TextStyle(
        fontFamily = googleFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    titleMedium = TextStyle(
        fontFamily = googleFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    titleSmall = TextStyle(
        fontFamily = googleFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = googleFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = googleFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = googleFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = googleFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = googleFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = googleFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp
    )
)