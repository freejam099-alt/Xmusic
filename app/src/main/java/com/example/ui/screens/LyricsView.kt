package com.example.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.LyricLine
import com.example.model.Song
import com.example.ui.theme.AppleMusicBackground
import com.example.ui.theme.AppleMusicCard
import com.example.ui.theme.AppleMusicRed
import com.example.ui.theme.AppleMusicTextPrimary
import com.example.ui.theme.AppleMusicTextSecondary
import com.example.ui.theme.SoftIcons

@Composable
fun LyricsView(
    song: Song?,
    lyrics: List<LyricLine>,
    activeLyricIndex: Int,
    onSeekTo: (Long) -> Unit,
    onClose: () -> Unit,
    onShareQuote: (String) -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(activeLyricIndex) {
        if (activeLyricIndex in lyrics.indices) {
            val target = (activeLyricIndex - 2).coerceAtLeast(0)
            listState.animateScrollToItem(target)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppleMusicBackground)
            .statusBarsPadding()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Apple Music Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onClose,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(AppleMusicCard)
                ) {
                    Icon(
                        imageVector = SoftIcons.CloseSmall,
                        contentDescription = "Tutup Lirik",
                        tint = AppleMusicTextPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "LIRIK REAL-TIME",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp,
                        color = AppleMusicRed
                    )
                    Text(
                        text = song?.title ?: "XMusic",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AppleMusicTextPrimary
                    )
                }

                IconButton(
                    onClick = {
                        val currentText = if (activeLyricIndex in lyrics.indices) {
                            lyrics[activeLyricIndex].text
                        } else {
                            lyrics.firstOrNull()?.text ?: "Mendengarkan ${song?.title}"
                        }
                        onShareQuote(currentText)
                    },
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(AppleMusicCard)
                ) {
                    Icon(
                        imageVector = SoftIcons.Share,
                        contentDescription = "Bagikan Kutipan",
                        tint = AppleMusicRed,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Lyrics Content
            if (lyrics.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text(
                            text = "♪ ♪ ♪",
                            fontSize = 32.sp,
                            color = AppleMusicRed
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Memuat lirik dari database terbuka LRCLIB...",
                            fontSize = 14.sp,
                            color = AppleMusicTextSecondary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    contentPadding = PaddingValues(top = 24.dp, bottom = 120.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    itemsIndexed(lyrics) { index, line ->
                        val isActive = index == activeLyricIndex
                        val isNear = Math.abs(index - activeLyricIndex) <= 1

                        val textColor by animateColorAsState(
                            targetValue = if (isActive) Color.White else AppleMusicTextSecondary,
                            animationSpec = spring(dampingRatio = 0.8f),
                            label = "textColor"
                        )

                        val alphaVal by animateFloatAsState(
                            targetValue = if (isActive) 1f else if (isNear) 0.6f else 0.3f,
                            animationSpec = spring(dampingRatio = 0.8f),
                            label = "alpha"
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { onSeekTo(line.timeMs) }
                                .padding(vertical = 4.dp, horizontal = 4.dp)
                        ) {
                            Text(
                                text = line.text,
                                fontSize = if (isActive) 26.sp else 21.sp,
                                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium,
                                color = textColor,
                                lineHeight = if (isActive) 34.sp else 28.sp,
                                modifier = Modifier.alpha(alphaVal)
                            )
                        }
                    }
                }
            }
        }
    }
}
