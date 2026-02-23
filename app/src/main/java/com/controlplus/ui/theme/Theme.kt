package com.controlplus.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val BgDark = Color(0xFF060B14)
val NeonGreen = Color(0xFF39FF88)
val ElectricBlue = Color(0xFF2FA8FF)
val NeonPurple = Color(0xFFB264FF)
val AlertRed = Color(0xFFFF4D57)
val MediumYellow = Color(0xFFFFD54F)

private val ControlDarkColors: ColorScheme = darkColorScheme(
    primary = ElectricBlue,
    secondary = NeonGreen,
    tertiary = NeonPurple,
    background = BgDark,
    surface = Color(0xFF0D1422),
    error = AlertRed,
    onBackground = Color.White,
    onSurface = Color(0xFFE6ECFF)
)

@Composable
fun ControlPlusTheme(content: @Composable () -> Unit) {
    val useDark = isSystemInDarkTheme()
    MaterialTheme(
        colorScheme = if (useDark) ControlDarkColors else ControlDarkColors,
        typography = Typography,
        content = content
    )
}
