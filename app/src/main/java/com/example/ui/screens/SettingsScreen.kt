package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.remote.YtMusicClient
import com.example.model.AppThemeMode
import com.example.model.AudioQuality
import com.example.model.SpatialMode
import com.example.service.PlayerState
import com.example.ui.theme.AppleMusicBackground
import com.example.ui.theme.AppleMusicBorder
import com.example.ui.theme.AppleMusicCard
import com.example.ui.theme.AppleMusicCardElevated
import com.example.ui.theme.AppleMusicRed
import com.example.ui.theme.AppleMusicTextPrimary
import com.example.ui.theme.AppleMusicTextSecondary
import com.example.ui.theme.AppleSystemGreen
import com.example.ui.theme.SoftIcons
import com.example.ui.viewmodel.AiSuggestionState

@Composable
fun SettingsScreen(
    playerState: PlayerState,
    themeMode: AppThemeMode,
    isSupermixVisible: Boolean,
    aiState: AiSuggestionState,
    apiKey: String = "",
    onApiKeyChange: (String) -> Unit = {},
    onThemeSelect: (AppThemeMode) -> Unit,
    onQualitySelect: (AudioQuality) -> Unit,
    onSpatialModeSelect: (SpatialMode) -> Unit,
    onToggleGapless: () -> Unit,
    onToggleSupermixVisible: (Boolean) -> Unit,
    onPlaySupermix: () -> Unit,
    onRescanLocal: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var inputKey by remember { mutableStateOf(apiKey) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(AppleMusicBackground)
            .statusBarsPadding(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp)
    ) {
        item {
            Column(modifier = Modifier.padding(bottom = 16.dp)) {
                Text(
                    text = "Pengaturan",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppleMusicTextPrimary,
                    letterSpacing = (-0.5).sp
                )
                Text(
                    text = "YouTube Data API v3 & konfigurasi audio Apple Music",
                    fontSize = 13.sp,
                    color = AppleMusicTextSecondary,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        // Section 1: Official YouTube Data API v3
        item {
            AppleSettingsSectionHeader(title = "YOUTUBE DATA API V3 RESMI", icon = SoftIcons.Equalizer)
        }

        item {
            AppleSettingsCard {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(AppleSystemGreen)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "YouTube Data API v3 Aktif & Terhubung",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AppleMusicTextPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Project ID: ${YtMusicClient.PROJECT_ID}",
                        fontSize = 12.sp,
                        color = AppleMusicTextSecondary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "OAuth Client ID:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = AppleMusicTextSecondary
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp, bottom = 12.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(AppleMusicCardElevated)
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = YtMusicClient.OAUTH_CLIENT_ID,
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            color = AppleMusicRed,
                            maxLines = 2
                        )
                    }

                    Text(
                        text = "Kunci API (YouTube Data API v3 Key):",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = AppleMusicTextPrimary
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = inputKey,
                        onValueChange = { inputKey = it },
                        placeholder = {
                            Text(
                                text = "Masukkan API Key (AIzaSy...)",
                                fontSize = 12.sp,
                                color = AppleMusicTextSecondary
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = AppleMusicCardElevated,
                            unfocusedContainerColor = AppleMusicCardElevated,
                            focusedBorderColor = AppleMusicRed,
                            unfocusedBorderColor = AppleMusicBorder,
                            focusedTextColor = AppleMusicTextPrimary,
                            unfocusedTextColor = AppleMusicTextPrimary,
                            cursorColor = AppleMusicRed
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            onApiKeyChange(inputKey.trim())
                            Toast.makeText(context, "API Key YouTube v3 berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppleMusicRed,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Simpan & Segarkan Data YouTube", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        // Section 2: Supermix Capsule
        item {
            Spacer(modifier = Modifier.height(20.dp))
            AppleSettingsSectionHeader(title = "KAPSUL SUPERMIX", icon = SoftIcons.SparkleAI)
        }

        item {
            AppleSettingsCard {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Tampilkan Kapsul di Beranda",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AppleMusicTextPrimary
                        )
                        Text(
                            text = "Akses cepat ke lagu rekomendasi YouTube Music",
                            fontSize = 12.sp,
                            color = AppleMusicTextSecondary
                        )
                    }

                    Switch(
                        checked = isSupermixVisible,
                        onCheckedChange = onToggleSupermixVisible,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = AppleMusicRed,
                            uncheckedThumbColor = Color.LightGray,
                            uncheckedTrackColor = AppleMusicCardElevated
                        )
                    )
                }
            }
        }

        // Section 3: Audio Lossless & Spatial
        item {
            Spacer(modifier = Modifier.height(20.dp))
            AppleSettingsSectionHeader(title = "KUALITAS AUDIO & EFEK SPATIAL", icon = SoftIcons.Spatial3D)
        }

        item {
            AppleSettingsCard {
                Column {
                    AudioQualityItem(
                        title = "Lossless Studio (FLAC 24-bit)",
                        subtitle = "Audio murni bitrate tertinggi tanpa kompresi",
                        isSelected = playerState.audioQuality == AudioQuality.LOSSLESS,
                        onClick = { onQualitySelect(AudioQuality.LOSSLESS) }
                    )
                    HorizontalDivider(color = AppleMusicBorder, thickness = 0.5.dp)
                    AudioQualityItem(
                        title = "High Quality (320 kbps AAC)",
                        subtitle = "Kualitas audio streaming premium standar",
                        isSelected = playerState.audioQuality == AudioQuality.HIGH,
                        onClick = { onQualitySelect(AudioQuality.HIGH) }
                    )
                    HorizontalDivider(color = AppleMusicBorder, thickness = 0.5.dp)
                    AudioQualityItem(
                        title = "Medium Saver (160 kbps Opus)",
                        subtitle = "Hemat kuota data dengan efisiensi tinggi",
                        isSelected = playerState.audioQuality == AudioQuality.MEDIUM,
                        onClick = { onQualitySelect(AudioQuality.MEDIUM) }
                    )
                }
            }
        }

        // Section 4: Tema Tampilan
        item {
            Spacer(modifier = Modifier.height(20.dp))
            AppleSettingsSectionHeader(title = "TEMA TAMPILAN", icon = SoftIcons.Settings)
        }

        item {
            AppleSettingsCard {
                Column {
                    ThemeOptionItem(
                        title = "OLED Pure Black (Apple Music Gelap)",
                        isSelected = themeMode == AppThemeMode.PURE_OLED,
                        onClick = { onThemeSelect(AppThemeMode.PURE_OLED) }
                    )
                    HorizontalDivider(color = AppleMusicBorder, thickness = 0.5.dp)
                    ThemeOptionItem(
                        title = "Apple Music Terang (Light)",
                        isSelected = themeMode == AppThemeMode.LIGHT,
                        onClick = { onThemeSelect(AppThemeMode.LIGHT) }
                    )
                    HorizontalDivider(color = AppleMusicBorder, thickness = 0.5.dp)
                    ThemeOptionItem(
                        title = "Mengikuti Sistem Perangkat",
                        isSelected = themeMode == AppThemeMode.SYSTEM,
                        onClick = { onThemeSelect(AppThemeMode.SYSTEM) }
                    )
                }
            }
        }

        // Section 5: Tentang Aplikasi
        item {
            Spacer(modifier = Modifier.height(20.dp))
            AppleSettingsSectionHeader(title = "TENTANG XMusic", icon = SoftIcons.Home)
        }

        item {
            AppleSettingsCard {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "XMusic v1.0 (Apple Music Inspired)",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppleMusicTextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Klien musik open source bertenaga YouTube Data API v3 resmi. Desain antarmuka, tata letak, dan icon terinspirasi murni dari Apple Music yang elegan dan minimalis.",
                        fontSize = 12.sp,
                        color = AppleMusicTextSecondary,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun AppleSettingsSectionHeader(title: String, icon: ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = AppleMusicRed,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = AppleMusicTextSecondary,
            letterSpacing = 0.8.sp
        )
    }
}

@Composable
fun AppleSettingsCard(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(AppleMusicCard)
            .border(0.8.dp, AppleMusicBorder, RoundedCornerShape(14.dp))
    ) {
        content()
    }
}

@Composable
fun AudioQualityItem(
    title: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) AppleMusicRed else AppleMusicTextPrimary
            )
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = AppleMusicTextSecondary
            )
        }

        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(AppleMusicRed)
            )
        }
    }
}

@Composable
fun ThemeOptionItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) AppleMusicRed else AppleMusicTextPrimary
        )

        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(AppleMusicRed)
            )
        }
    }
}
