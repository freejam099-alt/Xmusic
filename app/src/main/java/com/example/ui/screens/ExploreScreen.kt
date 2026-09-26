package com.example.ui.screens

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AudioQuality
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

data class MusicCategoryItem(val name: String, val color: Color)

@Composable
fun ExploreScreen(
    currentCategory: String,
    songs: List<Song>,
    playerState: PlayerState,
    downloadProgress: Map<String, Int>,
    onCategorySelect: (String) -> Unit,
    onSongSelect: (Song, List<Song>) -> Unit,
    onFavoriteToggle: (Song) -> Unit,
    onDownloadSong: (Song, AudioQuality) -> Unit,
    onDeleteDownload: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf(
        MusicCategoryItem("Pop", Color(0xFFE83A59)),
        MusicCategoryItem("Indonesian Hits", Color(0xFFD64545)),
        MusicCategoryItem("Ballad", Color(0xFF388E3C)),
        MusicCategoryItem("Acoustic", Color(0xFFE65100)),
        MusicCategoryItem("Lo-Fi & Chill", Color(0xFF5C6BC0)),
        MusicCategoryItem("R&B & Soul", Color(0xFF8E24AA)),
        MusicCategoryItem("Rock", Color(0xFF37474F)),
        MusicCategoryItem("Dance & EDM", Color(0xFF00897B))
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(AppleMusicBackground)
            .statusBarsPadding(),
        contentPadding = PaddingValues(bottom = 140.dp)
    ) {
        item {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                Text(
                    text = "Jelajahi",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppleMusicTextPrimary,
                    letterSpacing = (-0.5).sp
                )
                Text(
                    text = "Kategori musik & tangga lagu resmi YouTube Data API v3",
                    fontSize = 13.sp,
                    color = AppleMusicTextSecondary,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        // Apple Music Browse Genre Tiles Grid
        item {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text = "Jelajahi Menurut Kategori",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppleMusicTextPrimary,
                    letterSpacing = (-0.3).sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // 2-column grid of Apple Music category tiles
                val chunked = categories.chunked(2)
                chunked.forEach { rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        rowItems.forEach { cat ->
                            val isSelected = currentCategory.equals(cat.name, ignoreCase = true)
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(68.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(cat.color.copy(alpha = if (isSelected) 0.85f else 0.5f))
                                    .border(
                                        width = if (isSelected) 1.5.dp else 0.8.dp,
                                        color = if (isSelected) Color.White else AppleMusicBorder,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable { onCategorySelect(cat.name) }
                                    .padding(horizontal = 14.dp, vertical = 12.dp),
                                contentAlignment = Alignment.BottomStart
                            ) {
                                Text(
                                    text = cat.name,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section: "Daftar Lagu Kategori"
        item {
            Column(modifier = Modifier.padding(top = 18.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tangga Lagu: $currentCategory",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppleMusicTextPrimary,
                        letterSpacing = (-0.3).sp
                    )
                    Text(
                        text = "${songs.size} lagu",
                        fontSize = 12.sp,
                        color = AppleMusicTextSecondary
                    )
                }

                songs.forEach { song ->
                    val isCurrent = playerState.currentSong?.id == song.id
                    val isPlaying = isCurrent && playerState.isPlaying

                    SongItemRow(
                        song = song,
                        isCurrentSong = isCurrent,
                        isPlaying = isPlaying,
                        downloadProgress = downloadProgress[song.id],
                        onClick = { onSongSelect(song, songs) },
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
