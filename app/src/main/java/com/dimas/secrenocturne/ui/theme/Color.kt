package com.dimas.secrenocturne.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val VoidObsidian = Color(0xFF030508)
val MidnightAtmosphere = Color(0xFF11131D)
val NocturneSurface = Color(0xFF1D1F2A)
val NocturneSurfaceLow = Color(0xFF191B26)
val NocturneSurfaceHigh = Color(0xFF272935)

val StarlightGold = Color(0xFFF6C85F)
val StarlightGoldLight = Color(0xFFFFE29A)
val StarlightGoldDark = Color(0xFF251A00)

val NebulaCyan = Color(0xFF7BD0FF)
val TwilightLavender = Color(0xFFC084FC)

val TextPrimary = Color(0xFFE1E1F1)
val TextMuted = Color(0xFF94A3B8)
val BorderSubtle = Color(0x1AFFFFFF)

@Immutable
data class NocturnePalette(
    val isDark: Boolean,
    val background: Color,
    val surface: Color,
    val surfaceLow: Color,
    val surfaceHigh: Color,
    val cardBackground: Color,
    val textPrimary: Color,
    val textMuted: Color,
    val borderSubtle: Color,
    val gold: Color,
    val goldLight: Color,
    val goldDark: Color,
    val cyan: Color,
    val lavender: Color,
    val bottomNavBg: Color
)

val DarkPalette = NocturnePalette(
    isDark = true,
    background = MidnightAtmosphere,
    surface = NocturneSurface,
    surfaceLow = NocturneSurfaceLow,
    surfaceHigh = NocturneSurfaceHigh,
    cardBackground = Color(0xFF131522),
    textPrimary = TextPrimary,
    textMuted = TextMuted,
    borderSubtle = BorderSubtle,
    gold = StarlightGold,
    goldLight = StarlightGoldLight,
    goldDark = StarlightGoldDark,
    cyan = NebulaCyan,
    lavender = TwilightLavender,
    bottomNavBg = Color(0xFF0E101B)
)

val LightPalette = NocturnePalette(
    isDark = false,
    background = Color(0xFFF6F4ED),
    surface = Color(0xFFFFFFFF),
    surfaceLow = Color(0xFFEDE9DF),
    surfaceHigh = Color(0xFFE3DDD0),
    cardBackground = Color(0xFFFAF8F3),
    textPrimary = Color(0xFF1E2130),
    textMuted = Color(0xFF64748B),
    borderSubtle = Color(0x1F000000),
    gold = Color(0xFFC98A16),
    goldLight = Color(0xFFE2A127),
    goldDark = Color(0xFFFFFFFF),
    cyan = Color(0xFF0284C7),
    lavender = Color(0xFF8B5CF6),
    bottomNavBg = Color(0xFFFFFFFF)
)

val LocalNocturnePalette = staticCompositionLocalOf { DarkPalette }
