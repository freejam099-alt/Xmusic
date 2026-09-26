package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.model.AudioQuality
import com.example.model.Playlist
import com.example.model.Song
import com.example.service.PlayerState
import com.example.ui.components.SongItemRow
import com.example.ui.theme.AppleMusicBackground
import com.example.ui.theme.AppleMusicBorder
import com.example.ui.theme.AppleMusicCard
import com.example.ui.theme.AppleMusicRed
import com.example.ui.theme.AppleMusicTextPrimary
import com.example.ui.theme.AppleMusicTextSecondary
import com.example.ui.theme.SoftIcons

data class LibraryTab(val id: String, val title: String, val icon: ImageVector)

@Composable
fun LibraryScreen(
    favoriteSongs: List<Song>,
    downloadedSongs: List<Song>,
    localSongs: List<Song>,
    playlists: List<Playlist>,
    playerState: PlayerState,
    downloadProgress: Map<String, Int>,
    onSongSelect: (Song, List<Song>) -> Unit,
    onFavoriteToggle: (Song) -> Unit,
    onDownloadSong: (Song, AudioQuality) -> Unit,
    onDeleteDownload: (String) -> Unit,
    onRescanLocal: () -> Unit,
    onCreatePlaylistClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableStateOf("favorites") }

    val tabs = listOf(
        LibraryTab("favorites", "Favorit (${favoriteSongs.size})", SoftIcons.HeartFilled),
        LibraryTab("offline", "Download (${downloadedSongs.size})", SoftIcons.Download),
        LibraryTab("playlists", "Playlist (${playlists.size})", SoftIcons.Library),
        LibraryTab("local", "Lokal (${localSongs.size})", SoftIcons.PhoneLocal)
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onRescanLocal()
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(AppleMusicBackground)
            .statusBarsPadding(),
        contentPadding = PaddingValues(bottom = 140.dp)
    ) {
        // Apple Music Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Perpustakaan",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppleMusicTextPrimary,
                        letterSpacing = (-0.5).sp
                    )
                    Text(
                        text = "Koleksi musik favorit, playlist, dan unduhan offline",
                        fontSize = 13.sp,
                        color = AppleMusicTextSecondary,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                IconButton(
                    onClick = onCreatePlaylistClick,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(AppleMusicCard)
                        .border(0.8.dp, AppleMusicBorder, CircleShape)
                ) {
                    Icon(
                        imageVector = SoftIcons.Add,
                        contentDescription = "Buat Playlist",
                        tint = AppleMusicRed,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // Apple Music Segmented / Pill Filter
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tabs.forEach { tab ->
                    val isSelected = selectedTab == tab.id
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) AppleMusicRed else AppleMusicCard)
                            .border(
                                width = 0.8.dp,
                                color = if (isSelected) AppleMusicRed else AppleMusicBorder,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable { selectedTab = tab.id }
                            .padding(horizontal = 14.dp, vertical = 7.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = null,
                                tint = if (isSelected) Color.White else AppleMusicTextSecondary,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = tab.title,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (isSelected) Color.White else AppleMusicTextPrimary
                            )
                        }
                    }
                }
            }
        }

        // Content Based on Selected Tab
        when (selectedTab) {
            "favorites" -> {
                if (favoriteSongs.isEmpty()) {
                    item {
                        EmptyStateView(
                            icon = SoftIcons.HeartFilled,
                            title = "Belum Ada Lagu Favorit",
                            subtitle = "Tekan ikon hati pada lagu manapun dari YouTube Music untuk menyimpannya di sini."
                        )
                    }
                } else {
                    items(favoriteSongs) { song ->
                        val isCurrent = playerState.currentSong?.id == song.id
                        val isPlaying = isCurrent && playerState.isPlaying

                        SongItemRow(
                            song = song,
                            isCurrentSong = isCurrent,
                            isPlaying = isPlaying,
                            downloadProgress = downloadProgress[song.id],
                            onClick = { onSongSelect(song, favoriteSongs) },
                            onFavoriteClick = { onFavoriteToggle(song) },
                            onDownloadClick = { quality -> onDownloadSong(song, quality) },
                            onDeleteDownloadClick = { onDeleteDownload(song.id) },
                            onAddToPlaylistClick = {}
                        )
                    }
                }
            }

            "offline" -> {
                if (downloadedSongs.isEmpty()) {
                    item {
                        EmptyStateView(
                            icon = SoftIcons.Download,
                            title = "Tidak Ada Musik Tersimpan Offline",
                            subtitle = "Download lagu dari YouTube Music dengan kualitas Lossless untuk mendengarkan tanpa kuota internet."
                        )
                    }
                } else {
                    items(downloadedSongs) { song ->
                        val isCurrent = playerState.currentSong?.id == song.id
                        val isPlaying = isCurrent && playerState.isPlaying

                        SongItemRow(
                            song = song,
                            isCurrentSong = isCurrent,
                            isPlaying = isPlaying,
                            downloadProgress = downloadProgress[song.id],
                            onClick = { onSongSelect(song, downloadedSongs) },
                            onFavoriteClick = { onFavoriteToggle(song) },
                            onDownloadClick = { quality -> onDownloadSong(song, quality) },
                            onDeleteDownloadClick = { onDeleteDownload(song.id) },
                            onAddToPlaylistClick = {}
                        )
                    }
                }
            }

            "local" -> {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Musik Penyimpanan Perangkat",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = AppleMusicTextPrimary
                        )
                        Button(
                            onClick = {
                                val perm = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    Manifest.permission.READ_MEDIA_AUDIO
                                } else {
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                                }
                                if (ContextCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED) {
                                    onRescanLocal()
                                } else {
                                    permissionLauncher.launch(perm)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = AppleMusicRed, contentColor = Color.White),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Pindai File", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }

                if (localSongs.isEmpty()) {
                    item {
                        EmptyStateView(
                            icon = SoftIcons.PhoneLocal,
                            title = "Tidak Ada Musik Lokal Ditemukan",
                            subtitle = "Tekan tombol 'Pindai File' untuk membaca file audio yang ada di penyimpanan ponsel Anda."
                        )
                    }
                } else {
                    items(localSongs) { song ->
                        val isCurrent = playerState.currentSong?.id == song.id
                        val isPlaying = isCurrent && playerState.isPlaying

                        SongItemRow(
                            song = song,
                            isCurrentSong = isCurrent,
                            isPlaying = isPlaying,
                            downloadProgress = downloadProgress[song.id],
                            onClick = { onSongSelect(song, localSongs) },
                            onFavoriteClick = { onFavoriteToggle(song) },
                            onDownloadClick = { quality -> onDownloadSong(song, quality) },
                            onDeleteDownloadClick = { onDeleteDownload(song.id) },
                            onAddToPlaylistClick = {}
                        )
                    }
                }
            }

            "playlists" -> {
                if (playlists.isEmpty()) {
                    item {
                        EmptyStateView(
                            icon = SoftIcons.Library,
                            title = "Belum Ada Playlist",
                            subtitle = "Buat playlist Anda sendiri untuk mengatur lagu YouTube Music sesuai selera."
                        )
                    }
                } else {
                    items(playlists) { pl ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(AppleMusicCard),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = SoftIcons.Library,
                                    contentDescription = null,
                                    tint = AppleMusicRed,
                                    modifier = Modifier.size(22.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column {
                                Text(
                                    text = pl.name,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = AppleMusicTextPrimary
                                )
                                Text(
                                    text = "${pl.songCount} lagu • ${pl.description.ifEmpty { "Koleksi Playlist" }}",
                                    fontSize = 12.sp,
                                    color = AppleMusicTextSecondary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyStateView(
    icon: ImageVector,
    title: String,
    subtitle: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(AppleMusicCard),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = AppleMusicRed,
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = title,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = AppleMusicTextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = subtitle,
            fontSize = 13.sp,
            color = AppleMusicTextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp
        )
    }
}
