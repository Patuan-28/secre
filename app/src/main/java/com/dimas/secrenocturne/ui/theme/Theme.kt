package com.dimas.secrenocturne.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = StarlightGold,
    secondary = NebulaCyan,
    tertiary = TwilightLavender,
    background = MidnightAtmosphere,
    surface = NocturneSurface,
    onPrimary = StarlightGoldDark,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFC98A16),
    secondary = Color(0xFF0284C7),
    tertiary = Color(0xFF8B5CF6),
    background = Color(0xFFF6F4ED),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color(0xFFFFFFFF),
    onBackground = Color(0xFF1E2130),
    onSurface = Color(0xFF1E2130)
)

@Composable
fun SecreNocturneTheme(
    isDark: Boolean = true,
    content: @Composable () -> Unit
) {
    val palette = if (isDark) DarkPalette else LightPalette
    val colorScheme = if (isDark) DarkColorScheme else LightColorScheme

    CompositionLocalProvider(LocalNocturnePalette provides palette) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
