package com.mabn.calendarlibrary.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val Beige = Color(0xFFF2F5DE)
val TeaGreen = Color(0xFFD0FFB7)
val MintGreen = Color(0xFFB2FFA8)

val appGradientStart = Color(0xFF2096F3)

//Text color
@get:Composable
val Colors.textColor: Color get() = if (isLight) Color.Black else Color.White
