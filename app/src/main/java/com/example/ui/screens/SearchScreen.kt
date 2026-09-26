package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    query: String,
    results: List<Song>,
    isSearching: Boolean,
    playerState: PlayerState,
    downloadProgress: Map<String, Int>,
    onQueryChange: (String) -> Unit,
    onSongSelect: (Song, List<Song>) -> Unit,
    onFavoriteToggle: (Song) -> Unit,
    onDownloadSong: (Song, AudioQuality) -> Unit,
    onDeleteDownload: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val trendingKeywords = listOf("The Weeknd", "Die With A Smile", "Billie Eilish", "Komang", "Espresso", "Juicy Luicy", "Lofi Girl", "Ed Sheeran")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(AppleMusicBackground)
            .statusBarsPadding(),
        contentPadding = PaddingValues(bottom = 140.dp)
    ) {
        item {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)) {
                Text(
                    text = "Cari",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppleMusicTextPrimary,
                    letterSpacing = (-0.5).sp
                )
                Text(
                    text = "Cari di katalog YouTube Data API v3 resmi",
                    fontSize = 13.sp,
                    color = AppleMusicTextSecondary,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        // Apple Music Search Input Field
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp)
            ) {
                OutlinedTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    placeholder = {
                        Text(
                            text = "Artis, lagu, atau channel YouTube...",
                            color = AppleMusicTextSecondary,
                            fontSize = 14.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = SoftIcons.Search,
                            contentDescription = null,
                            tint = AppleMusicRed,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        if (isSearching) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                color = AppleMusicRed,
                                strokeWidth = 2.dp
                            )
                        } else if (query.isNotEmpty()) {
                            IconButton(onClick = { onQueryChange("") }) {
                                Icon(
                                    imageVector = SoftIcons.CloseSmall,
                                    contentDescription = "Hapus",
                                    tint = AppleMusicTextSecondary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = AppleMusicCard,
                        unfocusedContainerColor = AppleMusicCard,
                        focusedBorderColor = AppleMusicRed,
                        unfocusedBorderColor = AppleMusicBorder,
                        focusedTextColor = AppleMusicTextPrimary,
                        unfocusedTextColor = AppleMusicTextPrimary,
                        cursorColor = AppleMusicRed
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("search_text_field")
                )
            }
        }

        // Suggestions / Trending Keywords (Shown when query is empty)
        if (query.isEmpty()) {
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
                    Text(
                        text = "Paling Sering Dicari",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppleMusicTextPrimary,
                        letterSpacing = (-0.2).sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        trendingKeywords.forEach { tag ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(AppleMusicCard)
                                    .border(0.8.dp, AppleMusicBorder, RoundedCornerShape(16.dp))
                                    .clickable { onQueryChange(tag) }
                                    .padding(horizontal = 14.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = tag,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = AppleMusicTextPrimary
                                )
                            }
                        }
                    }
                }
            }
        }

        // Search Results List
        if (results.isNotEmpty()) {
            item {
                Text(
                    text = "Hasil YouTube Music (${results.size})",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppleMusicTextPrimary,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                )
            }

            items(results) { song ->
                val isCurrent = playerState.currentSong?.id == song.id
                val isPlaying = isCurrent && playerState.isPlaying

                SongItemRow(
                    song = song,
                    isCurrentSong = isCurrent,
                    isPlaying = isPlaying,
                    downloadProgress = downloadProgress[song.id],
                    onClick = { onSongSelect(song, results) },
                    onFavoriteClick = { onFavoriteToggle(song) },
                    onDownloadClick = { quality -> onDownloadSong(song, quality) },
                    onDeleteDownloadClick = { onDeleteDownload(song.id) },
                    onAddToPlaylistClick = {}
                )
            }
        } else if (query.isNotEmpty() && !isSearching) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Tidak Ada Hasil untuk \"$query\"",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AppleMusicTextPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Coba gunakan kata kunci artis atau judul lagu lain",
                            fontSize = 13.sp,
                            color = AppleMusicTextSecondary
                        )
                    }
                }
            }
        }
    }
}
