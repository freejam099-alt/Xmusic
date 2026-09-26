package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.model.AppThemeMode

private val AppleMusicDarkColorScheme = darkColorScheme(
    primary = AppleMusicRed,
    onPrimary = Color.White,
    primaryContainer = AppleMusicCardElevated,
    onPrimaryContainer = AppleMusicRed,
    secondary = AppleMusicCardElevated,
    onSecondary = AppleMusicTextPrimary,
    tertiary = AppleSystemGreen,
    background = AppleMusicBackground,
    surface = AppleMusicCard,
    surfaceVariant = AppleMusicCardElevated,
    onBackground = AppleMusicTextPrimary,
    onSurface = AppleMusicTextPrimary,
    onSurfaceVariant = AppleMusicTextSecondary,
    outline = AppleMusicBorder
)

private val AppleMusicElevatedColorScheme = darkColorScheme(
    primary = AppleMusicRed,
    onPrimary = Color.White,
    primaryContainer = AppleMusicCardElevated,
    onPrimaryContainer = AppleMusicRed,
    secondary = AppleMusicCard,
    onSecondary = AppleMusicTextPrimary,
    tertiary = AppleSystemOrange,
    background = AppleMusicCard,
    surface = AppleMusicCardElevated,
    surfaceVariant = Color(0xFF3A3A3C),
    onBackground = AppleMusicTextPrimary,
    onSurface = AppleMusicTextPrimary,
    onSurfaceVariant = AppleMusicTextSecondary,
    outline = AppleMusicBorder
)

private val AppleMusicLightColorScheme = lightColorScheme(
    primary = AppleMusicRed,
    onPrimary = Color.White,
    primaryContainer = AppleLightCard,
    onPrimaryContainer = AppleMusicRed,
    secondary = AppleLightCardElevated,
    onSecondary = AppleLightTextPrimary,
    tertiary = AppleSystemGreen,
    background = AppleLightBackground,
    surface = AppleLightBackground,
    surfaceVariant = AppleLightCard,
    onBackground = AppleLightTextPrimary,
    onSurface = AppleLightTextPrimary,
    onSurfaceVariant = AppleLightTextSecondary,
    outline = AppleLightBorder
)

@Composable
fun XMusicTheme(
    themeMode: AppThemeMode = AppThemeMode.PURE_OLED,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val systemDark = isSystemInDarkTheme()

    val colorScheme = when (themeMode) {
        AppThemeMode.PURE_OLED -> AppleMusicDarkColorScheme
        AppThemeMode.DARK_EXPRESSIVE -> AppleMusicElevatedColorScheme
        AppThemeMode.LIGHT -> AppleMusicLightColorScheme
        AppThemeMode.AMBIENT_AUTO -> if (systemDark) AppleMusicDarkColorScheme else AppleMusicLightColorScheme
        AppThemeMode.SYSTEM -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (systemDark) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            } else {
                if (systemDark) AppleMusicDarkColorScheme else AppleMusicLightColorScheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
