package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.AudioQuality
import com.example.model.Song
import com.example.ui.theme.AppleMusicCard
import com.example.ui.theme.AppleMusicRed
import com.example.ui.theme.AppleMusicTextPrimary
import com.example.ui.theme.AppleMusicTextSecondary
import com.example.ui.theme.AppleSystemGreen
import com.example.ui.theme.SoftIcons

@Composable
fun SongItemRow(
    song: Song,
    isCurrentSong: Boolean,
    isPlaying: Boolean,
    downloadProgress: Int? = null,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onDownloadClick: (AudioQuality) -> Unit,
    onDeleteDownloadClick: () -> Unit,
    onAddToPlaylistClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .testTag("song_item_${song.id}"),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Thumbnail with Apple Music rounded corners
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(AppleMusicCard),
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
                    modifier = Modifier.size(24.dp)
                )
            }

            // Playing Animated Visualizer Overlay
            if (isCurrentSong && isPlaying) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0x99000000)),
                    contentAlignment = Alignment.Center
                ) {
                    AppleEqualizerAnimation()
                }
            }
        }

        Spacer(modifier = Modifier.width(14.dp))

        // Title & Artist
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = song.title,
                fontSize = 15.sp,
                fontWeight = if (isCurrentSong) FontWeight.Bold else FontWeight.Medium,
                color = if (isCurrentSong) AppleMusicRed else AppleMusicTextPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(2.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                // Apple Music Quality Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(3.dp))
                        .background(Color(0x28FFFFFF))
                        .padding(horizontal = 4.dp, vertical = 1.dp)
                ) {
                    Text(
                        text = "LOSSLESS",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppleMusicTextSecondary
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = song.artist,
                    fontSize = 13.sp,
                    color = AppleMusicTextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // Download status indicator
        if (downloadProgress != null && downloadProgress in 0..99) {
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .padding(end = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { downloadProgress / 100f },
                    modifier = Modifier.size(18.dp),
                    color = AppleMusicRed,
                    strokeWidth = 2.dp
                )
            }
        } else if (song.isDownloaded) {
            Icon(
                imageVector = SoftIcons.DownloadDone,
                contentDescription = "Tersimpan Offline",
                tint = AppleSystemGreen,
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 6.dp)
            )
        }

        // Favorite Button
        IconButton(
            onClick = onFavoriteClick,
            modifier = Modifier.size(34.dp)
        ) {
            Icon(
                imageVector = if (song.isFavorite) SoftIcons.HeartFilled else SoftIcons.HeartOutlined,
                contentDescription = "Favorit",
                tint = if (song.isFavorite) AppleMusicRed else AppleMusicTextSecondary,
                modifier = Modifier.size(18.dp)
            )
        }

        // More Options Menu
        Box {
            IconButton(
                onClick = { menuExpanded = true },
                modifier = Modifier.size(34.dp)
            ) {
                Icon(
                    imageVector = SoftIcons.MoreVert,
                    contentDescription = "Opsi Lainnya",
                    tint = AppleMusicTextSecondary,
                    modifier = Modifier.size(18.dp)
                )
            }

            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false },
                modifier = Modifier.background(AppleMusicCard)
            ) {
                if (!song.isDownloaded) {
                    DropdownMenuItem(
                        text = { Text("Download Lossless (320K/FLAC)") },
                        leadingIcon = { Icon(SoftIcons.Download, contentDescription = null, tint = AppleMusicRed, modifier = Modifier.size(18.dp)) },
                        onClick = {
                            menuExpanded = false
                            onDownloadClick(AudioQuality.LOSSLESS)
                        }
                    )
                } else {
                    DropdownMenuItem(
                        text = { Text("Hapus Download") },
                        leadingIcon = { Icon(SoftIcons.CloseSmall, contentDescription = null, tint = AppleMusicRed, modifier = Modifier.size(18.dp)) },
                        onClick = {
                            menuExpanded = false
                            onDeleteDownloadClick()
                        }
                    )
                }

                DropdownMenuItem(
                    text = { Text("Tambahkan ke Playlist") },
                    leadingIcon = { Icon(SoftIcons.Library, contentDescription = null, tint = AppleMusicTextPrimary, modifier = Modifier.size(18.dp)) },
                    onClick = {
                        menuExpanded = false
                        onAddToPlaylistClick()
                    }
                )
            }
        }
    }
}

@Composable
fun AppleEqualizerAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "eq_bars")
    val h1 by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(tween(350, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "bar1"
    )
    val h2 by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0.2f,
        animationSpec = infiniteRepeatable(tween(420, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "bar2"
    )
    val h3 by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(tween(300, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "bar3"
    )

    Row(
        modifier = Modifier.size(18.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Box(
            modifier = Modifier
                .width(3.dp)
                .fillMaxHeight(h1)
                .clip(RoundedCornerShape(2.dp))
                .background(AppleMusicRed)
        )
        Box(
            modifier = Modifier
                .width(3.dp)
                .fillMaxHeight(h2)
                .clip(RoundedCornerShape(2.dp))
                .background(AppleMusicRed)
        )
        Box(
            modifier = Modifier
                .width(3.dp)
                .fillMaxHeight(h3)
                .clip(RoundedCornerShape(2.dp))
                .background(AppleMusicRed)
        )
    }
}

fun formatDuration(ms: Long): String {
    val totalSeconds = (ms / 1000).coerceAtLeast(0)
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%d:%02d", minutes, seconds)
}
