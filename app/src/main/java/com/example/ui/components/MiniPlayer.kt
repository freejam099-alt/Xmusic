package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.SpatialMode
import com.example.service.PlayerState
import com.example.ui.theme.AppleMusicBorder
import com.example.ui.theme.AppleMusicCard
import com.example.ui.theme.AppleMusicFrosted
import com.example.ui.theme.AppleMusicRed
import com.example.ui.theme.AppleMusicTextPrimary
import com.example.ui.theme.AppleMusicTextSecondary
import com.example.ui.theme.SoftIcons

@Composable
fun MiniPlayer(
    playerState: PlayerState,
    onExpandClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onSkipNextClick: () -> Unit,
    onSpatialClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val song = playerState.currentSong ?: return

    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 4.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(16.dp),
                    spotColor = Color(0x90000000),
                    ambientColor = Color(0x60000000)
                )
                .clip(RoundedCornerShape(16.dp))
                .background(AppleMusicFrosted)
                .border(0.8.dp, AppleMusicBorder, RoundedCornerShape(16.dp))
                .clickable { onExpandClick() }
                .testTag("mini_player_root")
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // YouTube Cover Thumbnail
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(10.dp))
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
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Track & Channel Info
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = song.title,
                            fontSize = 14.sp,
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

                    // Spatial Mode Quick Button
                    if (playerState.spatialMode != SpatialMode.OFF) {
                        IconButton(
                            onClick = onSpatialClick,
                            modifier = Modifier.size(34.dp)
                        ) {
                            Icon(
                                imageVector = SoftIcons.Spatial3D,
                                contentDescription = "Spatial Audio Mode",
                                tint = AppleMusicRed,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    // Apple Music Play / Pause Button
                    IconButton(
                        onClick = onPlayPauseClick,
                        modifier = Modifier
                            .size(38.dp)
                            .testTag("mini_player_play_pause")
                    ) {
                        if (playerState.isBuffering) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                color = AppleMusicRed,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                imageVector = if (playerState.isPlaying) SoftIcons.Pause else SoftIcons.Play,
                                contentDescription = if (playerState.isPlaying) "Jeda" else "Putar",
                                tint = AppleMusicRed,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    // Next Button
                    IconButton(
                        onClick = onSkipNextClick,
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(
                            imageVector = SoftIcons.SkipNext,
                            contentDescription = "Selanjutnya",
                            tint = AppleMusicTextPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Apple Music Subtle Bottom Progress Bar
                val maxDur = if (playerState.durationMs > 0) playerState.durationMs else 210000L
                val progressFraction = (playerState.currentPositionMs.toFloat() / maxDur.toFloat()).coerceIn(0f, 1f)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(Color(0x20FFFFFF))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progressFraction)
                            .height(2.dp)
                            .background(AppleMusicRed)
                    )
                }
            }
        }
    }
}
