package com.example.ui.screens

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.AudioQuality
import com.example.model.RepeatMode
import com.example.model.Song
import com.example.model.SpatialMode
import com.example.service.PlayerState
import com.example.ui.components.formatDuration
import com.example.ui.theme.AppleMusicBackground
import com.example.ui.theme.AppleMusicBorder
import com.example.ui.theme.AppleMusicCard
import com.example.ui.theme.AppleMusicRed
import com.example.ui.theme.AppleMusicTextPrimary
import com.example.ui.theme.AppleMusicTextSecondary
import com.example.ui.theme.AppleSystemGreen
import com.example.ui.theme.SoftIcons

@Composable
fun PlayerScreen(
    playerState: PlayerState,
    onCollapse: () -> Unit,
    onPlayPause: () -> Unit,
    onSeekTo: (Long) -> Unit,
    onSkipNext: () -> Unit,
    onSkipPrevious: () -> Unit,
    onToggleRepeat: () -> Unit,
    onToggleShuffle: () -> Unit,
    onToggleFavorite: (Song) -> Unit,
    onDownload: (Song, AudioQuality) -> Unit,
    onOpenLyrics: () -> Unit,
    onOpenSpatialDialog: () -> Unit,
    onOpenQualityDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler {
        onCollapse()
    }

    val context = LocalContext.current
    val song = playerState.currentSong ?: return

    var isDraggingSlider by remember { mutableStateOf(false) }
    var sliderValue by remember { mutableFloatStateOf(0f) }

    val currentPos = if (isDraggingSlider) {
        sliderValue.toLong()
    } else {
        playerState.currentPositionMs
    }

    val maxDuration = if (playerState.durationMs > 0) playerState.durationMs else (song.durationMs.takeIf { it > 0 } ?: 210000L)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppleMusicBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("player_screen_root")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Apple Music Header Bar (Chevron Down + YouTube API Badge + Share Sheet)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onCollapse,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(AppleMusicCard)
                        .testTag("player_collapse_button")
                ) {
                    Icon(
                        imageVector = SoftIcons.ChevronDown,
                        contentDescription = "Tutup",
                        tint = AppleMusicTextPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Apple Music Center Capsule: YouTube Music Lossless
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(AppleMusicCard)
                        .border(0.8.dp, AppleMusicBorder, RoundedCornerShape(16.dp))
                        .clickable { onOpenQualityDialog() }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(AppleSystemGreen)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "YouTube Data API v3 Lossless",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AppleMusicTextPrimary
                        )
                    }
                }

                IconButton(
                    onClick = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "Mendengarkan '${song.title}' oleh ${song.artist} di XMusic (YouTube Music Client)\nhttps://www.youtube.com/watch?v=${song.id}"
                            )
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Bagikan Lagu"))
                    },
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(AppleMusicCard)
                ) {
                    Icon(
                        imageVector = SoftIcons.Share,
                        contentDescription = "Bagikan",
                        tint = AppleMusicTextPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Apple Music Album Artwork
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .aspectRatio(1f)
                        .shadow(
                            elevation = 24.dp,
                            shape = RoundedCornerShape(18.dp),
                            spotColor = Color(0x90000000),
                            ambientColor = Color(0x60000000)
                        )
                        .clip(RoundedCornerShape(18.dp))
                        .background(AppleMusicCard)
                        .border(0.8.dp, AppleMusicBorder, RoundedCornerShape(18.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (song.thumbnailUrl.isNotBlank()) {
                        AsyncImage(
                            model = song.thumbnailUrl,
                            contentDescription = song.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Icon(
                            imageVector = SoftIcons.MusicNote,
                            contentDescription = null,
                            tint = AppleMusicRed,
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }
            }

            // Track Title & YouTube Channel Info
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = song.title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppleMusicTextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        letterSpacing = (-0.3).sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = song.artist,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = AppleMusicRed,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Apple Music Favorite Heart Button
                IconButton(onClick = { onToggleFavorite(song) }) {
                    Icon(
                        imageVector = if (song.isFavorite) SoftIcons.HeartFilled else SoftIcons.HeartOutlined,
                        contentDescription = "Favorit",
                        tint = if (song.isFavorite) AppleMusicRed else AppleMusicTextSecondary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Apple Music Time Scrubber Slider
            Column(modifier = Modifier.fillMaxWidth()) {
                Slider(
                    value = currentPos.coerceIn(0L, maxDuration).toFloat(),
                    onValueChange = {
                        isDraggingSlider = true
                        sliderValue = it
                    },
                    onValueChangeFinished = {
                        onSeekTo(sliderValue.toLong())
                        isDraggingSlider = false
                    },
                    valueRange = 0f..maxDuration.toFloat(),
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTrackColor = AppleMusicRed,
                        inactiveTrackColor = Color(0x33FFFFFF)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = formatDuration(currentPos),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = AppleMusicTextSecondary
                    )
                    Text(
                        text = "-" + formatDuration((maxDuration - currentPos).coerceAtLeast(0L)),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = AppleMusicTextSecondary
                    )
                }
            }

            // Apple Music Playback Control Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Shuffle
                IconButton(onClick = onToggleShuffle) {
                    Icon(
                        imageVector = SoftIcons.Shuffle,
                        contentDescription = "Acak",
                        tint = if (playerState.isShuffle) AppleMusicRed else AppleMusicTextSecondary,
                        modifier = Modifier.size(22.dp)
                    )
                }

                // Previous
                IconButton(
                    onClick = onSkipPrevious,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = SoftIcons.SkipPrevious,
                        contentDescription = "Sebelumnya",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }

                // Apple Music Play / Pause Button
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .shadow(elevation = 12.dp, shape = CircleShape)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable { onPlayPause() }
                        .testTag("player_play_pause_button"),
                    contentAlignment = Alignment.Center
                ) {
                    if (playerState.isBuffering) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(28.dp),
                            color = Color.Black,
                            strokeWidth = 2.5.dp
                        )
                    } else {
                        Icon(
                            imageVector = if (playerState.isPlaying) SoftIcons.Pause else SoftIcons.Play,
                            contentDescription = if (playerState.isPlaying) "Jeda" else "Putar",
                            tint = Color.Black,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                // Next
                IconButton(
                    onClick = onSkipNext,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = SoftIcons.SkipNext,
                        contentDescription = "Selanjutnya",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }

                // Repeat
                IconButton(onClick = onToggleRepeat) {
                    Icon(
                        imageVector = when (playerState.repeatMode) {
                            RepeatMode.ONE -> SoftIcons.RepeatOne
                            else -> SoftIcons.Repeat
                        },
                        contentDescription = "Ulangi",
                        tint = if (playerState.repeatMode != RepeatMode.OFF) AppleMusicRed else AppleMusicTextSecondary,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            // Apple Music Bottom Action Toolbar: Lyrics, Spatial, Download
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                // Lyrics Sheet Toggle
                IconButton(
                    onClick = onOpenLyrics,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(AppleMusicCard)
                        .padding(horizontal = 8.dp)
                ) {
                    Icon(
                        imageVector = SoftIcons.Lyrics,
                        contentDescription = "Lirik",
                        tint = AppleMusicTextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Spatial Audio Mode Selector
                IconButton(
                    onClick = onOpenSpatialDialog,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (playerState.spatialMode != SpatialMode.OFF) AppleMusicCard else AppleMusicCard)
                ) {
                    Icon(
                        imageVector = SoftIcons.Spatial3D,
                        contentDescription = "Spatial Audio Effect",
                        tint = if (playerState.spatialMode != SpatialMode.OFF) AppleMusicRed else AppleMusicTextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Download Button
                IconButton(
                    onClick = { onDownload(song, AudioQuality.LOSSLESS) },
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(AppleMusicCard)
                ) {
                    Icon(
                        imageVector = if (song.isDownloaded) SoftIcons.DownloadDone else SoftIcons.Download,
                        contentDescription = "Unduh",
                        tint = if (song.isDownloaded) AppleSystemGreen else AppleMusicTextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
