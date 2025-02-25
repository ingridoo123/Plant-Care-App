package com.example.greenhub.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val DarkGray = Color(0xFF2A2A2A)
val DarkGray2 = Color(0xFF5c5c5c)
val LightGray = Color(0xFFD8D8D8)

val MainColor = Color(0xFF13693D)
val DarkGreen = Color(0xFF093D22)
val SecondaryGreen = Color(0xff438764)
val LightGreen = Color(0xFF72A78D)
val backGroundColor = Color(0xfff6f7f6)
val imageBackGroundColor = Color(0xffe9e9e9)

val ShimmerLightGray = Color(0xFFF6F7F6)
val ShimmerMediumGray = Color(0xFFE9E9E9)
val ShimmerDarkGray = Color(0xFF1D1D1D)



val Colors.descriptionColor
    @Composable
    get() = if (isLight) DarkGray.copy(alpha = 0.8f) else LightGray.copy(alpha = 0.5f)

val Colors.titleColor
    @Composable
    get() = if (isLight) DarkGray else LightGray