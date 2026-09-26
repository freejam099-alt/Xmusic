package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// =========================================================================
// Official Apple Music Design System Color Palette (iOS 18 SF-Inspired)
// NO Cyberpunk, NO Neon Cyan, NO Electric Violet. Pure Apple Elegance.
// =========================================================================

// Signature Apple Music Colors
val AppleMusicRed = Color(0xFFFA243C)            // Signature Apple Music Vibrance Red
val AppleMusicBackground = Color(0xFF000000)     // Pure OLED Black
val AppleMusicCard = Color(0xFF1C1C1E)           // Apple Secondary Grouped Background (Cards)
val AppleMusicCardElevated = Color(0xFF2C2C2E)   // Apple Tertiary Grouped Background (Pills/Elevated)
val AppleMusicBorder = Color(0x2EFFFFFF)         // Apple Subtle Separator Border (0.5dp white)
val AppleMusicFrosted = Color(0xEA1C1C1E)        // Apple Music Frosted Glass Surface
val AppleMusicTextPrimary = Color(0xFFFFFFFF)    // Apple Music Pure White Text
val AppleMusicTextSecondary = Color(0xFF8E8E93)  // Apple Music System Gray Secondary Text
val AppleMusicTextTertiary = Color(0xFF636366)   // Apple Music System Gray 3
val AppleSystemGreen = Color(0xFF34C759)         // Apple System Green (Lossless / Hi-Res / Status)
val AppleSystemBlue = Color(0xFF0A84FF)          // Apple System Blue
val AppleSystemOrange = Color(0xFFFF9F0A)        // Apple System Orange
val AppleSystemPink = Color(0xFFFF375F)          // Apple System Pink

// Light Mode Palette
val AppleLightBackground = Color(0xFFFFFFFF)
val AppleLightCard = Color(0xFFF2F2F7)
val AppleLightCardElevated = Color(0xFFE5E5EA)
val AppleLightBorder = Color(0x1F000000)
val AppleLightTextPrimary = Color(0xFF000000)
val AppleLightTextSecondary = Color(0xFF8E8E93)

// Safe backwards compatibility mappings (mapped strictly to Apple Music theme)
val OledBlack = AppleMusicBackground
val DarkBackground = AppleMusicBackground
val DarkSurface = AppleMusicCard
val DarkSurfaceVariant = AppleMusicCardElevated
val DarkSurfaceElevated = Color(0xFF3A3A3C)
val TextPrimaryDark = AppleMusicTextPrimary
val TextSecondaryDark = AppleMusicTextSecondary
val TextTertiaryDark = AppleMusicTextTertiary

val NeonCyan = AppleMusicRed
val ElectricViolet = AppleMusicCardElevated
val CoralRose = AppleMusicRed
val EmeraldMint = AppleSystemGreen
val AmberGlow = AppleSystemOrange

val IOSSystemBlue = AppleSystemBlue
val IOSSystemPink = AppleMusicRed
val IOSSystemIndigo = Color(0xFF5E5CE6)
val IOSSystemPurple = Color(0xFFBF5AF2)
val IOSSystemTeal = Color(0xFF64D2FF)
val IOSSystemMint = AppleSystemGreen
val IOSSystemCyan = AppleSystemBlue
val IOSSystemOrange = AppleSystemOrange
val IOSCardDark = AppleMusicCard
val IOSCardElevated = AppleMusicCardElevated
val IOSPillBackground = Color(0xD81C1C1E)
val IOSGlassBorder = AppleMusicBorder
val IOSSpecularHighlight = Color(0x20FFFFFF)

val GlassBackground = AppleMusicFrosted
val GlassBorder = AppleMusicBorder
val GlassSurface = Color(0x14FFFFFF)

val LightBackground = AppleLightBackground
val LightSurface = AppleLightBackground
val LightSurfaceVariant = AppleLightCard
val LightPrimary = AppleMusicRed
