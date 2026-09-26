package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.AudioQuality
import com.example.model.Song
import com.example.model.YouTubeChannel
import com.example.model.YouTubePlaylist
import com.example.service.PlayerState
import com.example.ui.components.SongItemRow
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
fun HomeScreen(
    aiState: AiSuggestionState,
    trendingSongs: List<Song>,
    youtubePlaylists: List<YouTubePlaylist> = emptyList(),
    youtubeChannels: List<YouTubeChannel> = emptyList(),
    playerState: PlayerState,
    downloadProgress: Map<String, Int>,
    isSupermixVisible: Boolean,
    onDismissSupermix: () -> Unit,
    onSongSelect: (Song, List<Song>) -> Unit,
    onFavoriteToggle: (Song) -> Unit,
    onDownloadSong: (Song, AudioQuality) -> Unit,
    onDeleteDownload: (String) -> Unit,
    onOpenSettings: () -> Unit,
    onOpenSpatialDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    val moods = listOf("Semua", "Pop", "Ballad", "Indo Hits", "Acoustic", "Lo-Fi", "Alternative", "R&B")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(AppleMusicBackground)
            .statusBarsPadding(),
        contentPadding = PaddingValues(bottom = 140.dp)
    ) {
        // Apple Music Header: "Dengarkan Sekarang" (Listen Now)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Dengarkan Sekarang",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppleMusicTextPrimary,
                        letterSpacing = (-0.5).sp
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(AppleSystemGreen)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "YouTube Data API v3 Resmi",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = AppleMusicTextSecondary
                        )
                    }
                }

                // Apple Music Profile / Settings Button
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(AppleMusicCardElevated)
                        .border(0.8.dp, AppleMusicBorder, CircleShape)
                        .clickable { onOpenSettings() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = SoftIcons.Settings,
                        contentDescription = "Pengaturan",
                        tint = AppleMusicTextPrimary,
                        modifier = Modifier.size(19.dp)
                    )
                }
            }
        }

        // Apple Music Genre / Mood Filter Bar
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                moods.forEachIndexed { index, mood ->
                    val isSelected = index == 0
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) AppleMusicRed else AppleMusicCard)
                            .border(
                                width = 0.8.dp,
                                color = if (isSelected) AppleMusicRed else AppleMusicBorder,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(horizontal = 14.dp, vertical = 7.dp)
                    ) {
                        Text(
                            text = mood,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (isSelected) Color.White else AppleMusicTextPrimary
                        )
                    }
                }
            }
        }

        // Apple Music Supermix AI Card (Sleek Apple Music card without cyberpunk)
        item {
            AnimatedVisibility(
                visible = isSupermixVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(AppleMusicCard)
                        .border(0.8.dp, AppleMusicBorder, RoundedCornerShape(16.dp))
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(AppleMusicRed),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = SoftIcons.SparkleAI,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Supermix YouTube Music",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = AppleMusicTextPrimary
                            )
                            Text(
                                text = "Lagu pilihan dari channel resmi YouTube",
                                fontSize = 11.sp,
                                color = AppleMusicTextSecondary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        // Play Supermix
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .background(AppleMusicRed)
                                .clickable {
                                    if (trendingSongs.isNotEmpty()) {
                                        onSongSelect(trendingSongs.first(), trendingSongs)
                                    }
                                }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = SoftIcons.Play,
                                    contentDescription = "Putar",
                                    tint = Color.White,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Putar",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(6.dp))

                        IconButton(
                            onClick = onDismissSupermix,
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = SoftIcons.CloseSmall,
                                contentDescription = "Tutup",
                                tint = AppleMusicTextSecondary,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }
                }
            }
        }

        // Apple Music Section: "Pilihan Utama" (Heavy Rotation / Featured Tracks)
        item {
            Column(modifier = Modifier.padding(top = 18.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Pilihan Utama",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppleMusicTextPrimary,
                        letterSpacing = (-0.3).sp
                    )
                    Text(
                        text = "Lihat Semua",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = AppleMusicRed
                    )
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(trendingSongs.take(6)) { song ->
                        AppleMusicCardItem(
                            song = song,
                            onClick = { onSongSelect(song, trendingSongs) }
                        )
                    }
                }
            }
        }

        // Section: "Daftar Putar Resmi YouTube Music" (Official YouTube Music Playlists)
        if (youtubePlaylists.isNotEmpty()) {
            item {
                Column(modifier = Modifier.padding(top = 22.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Daftar Putar Resmi YouTube",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = AppleMusicTextPrimary,
                            letterSpacing = (-0.3).sp
                        )
                        Text(
                            text = "Eksplor",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = AppleMusicRed
                        )
                    }

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        items(youtubePlaylists) { playlist ->
                            YouTubePlaylistItem(
                                playlist = playlist,
                                onClick = {
                                    if (trendingSongs.isNotEmpty()) {
                                        onSongSelect(trendingSongs.first(), trendingSongs)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

        // Section: "Artis & Channel Teratas" (Top Artists & Channels)
        if (youtubeChannels.isNotEmpty()) {
            item {
                Column(modifier = Modifier.padding(top = 22.dp)) {
                    Text(
                        text = "Artis & Channel Teratas",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppleMusicTextPrimary,
                        letterSpacing = (-0.3).sp,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                    )

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(youtubeChannels) { channel ->
                            YouTubeChannelItem(channel = channel)
                        }
                    }
                }
            }
        }

        // Section: "Lagu Terpopuler YouTube Music" (YouTube Music Hot Tracks)
        item {
            Column(modifier = Modifier.padding(top = 22.dp)) {
                Text(
                    text = "Lagu Terpopuler",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppleMusicTextPrimary,
                    letterSpacing = (-0.3).sp,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                )

                trendingSongs.forEach { song ->
                    val isCurrent = playerState.currentSong?.id == song.id
                    val isPlaying = isCurrent && playerState.isPlaying

                    SongItemRow(
                        song = song,
                        isCurrentSong = isCurrent,
                        isPlaying = isPlaying,
                        downloadProgress = downloadProgress[song.id],
                        onClick = { onSongSelect(song, trendingSongs) },
                        onFavoriteClick = { onFavoriteToggle(song) },
                        onDownloadClick = { quality -> onDownloadSong(song, quality) },
                        onDeleteDownloadClick = { onDeleteDownload(song.id) },
                        onAddToPlaylistClick = {}
                    )
                }
            }
        }
    }
}

@Composable
fun AppleMusicCardItem(
    song: Song,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .shadow(elevation = 6.dp, shape = RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(AppleMusicCard)
        ) {
            AsyncImage(
                model = song.thumbnailUrl,
                contentDescription = song.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Play badge
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color(0xD0000000)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = SoftIcons.Play,
                    contentDescription = "Putar",
                    tint = AppleMusicRed,
                    modifier = Modifier.size(14.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = song.title,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = AppleMusicTextPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = song.artist,
            fontSize = 12.sp,
            color = AppleMusicTextSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun YouTubePlaylistItem(
    playlist: YouTubePlaylist,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(140.dp)
                .shadow(elevation = 6.dp, shape = RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(AppleMusicCard)
        ) {
            AsyncImage(
                model = playlist.thumbnailUrl,
                contentDescription = playlist.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(6.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xCC000000))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "${playlist.itemCount} lagu",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = playlist.title,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = AppleMusicTextPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = playlist.channelTitle,
            fontSize = 11.sp,
            color = AppleMusicTextSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun YouTubeChannelItem(
    channel: YouTubeChannel
) {
    Column(
        modifier = Modifier.width(90.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(76.dp)
                .shadow(elevation = 4.dp, shape = CircleShape)
                .clip(CircleShape)
                .border(1.dp, AppleMusicBorder, CircleShape)
                .background(AppleMusicCard)
        ) {
            AsyncImage(
                model = channel.thumbnailUrl,
                contentDescription = channel.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = channel.title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = AppleMusicTextPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = channel.subscriberCount,
            fontSize = 10.sp,
            color = AppleMusicTextSecondary,
            maxLines = 1
        )
    }
}
