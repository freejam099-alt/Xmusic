package com.example

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.AudioQuality
import com.example.model.Song
import com.example.ui.components.AudioQualityDialog
import com.example.ui.components.CreatePlaylistDialog
import com.example.ui.components.MiniPlayer
import com.example.ui.components.ShareLyricDialog
import com.example.ui.components.SpatialAudioDialog
import com.example.ui.components.XMusicNavBar
import com.example.ui.screens.ExploreScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LibraryScreen
import com.example.ui.screens.LyricsView
import com.example.ui.screens.PlayerScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.theme.XMusicTheme
import com.example.ui.viewmodel.MusicViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: MusicViewModel = viewModel()
            val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()

            // Notification permission request for Android 13+
            val notificationPermissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ) { /* granted/denied */ }

            LaunchedEffect(Unit) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }

            XMusicTheme(themeMode = themeMode) {
                XMusicApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun XMusicApp(viewModel: MusicViewModel) {
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val playerState by viewModel.playerState.collectAsStateWithLifecycle()
    val isPlayerExpanded by viewModel.isPlayerExpanded.collectAsStateWithLifecycle()
    val showLyrics by viewModel.showLyricsSheet.collectAsStateWithLifecycle()
    val showSpatialDialog by viewModel.showSpatialDialog.collectAsStateWithLifecycle()
    val showQualityDialog by viewModel.showQualityDialog.collectAsStateWithLifecycle()
    val showShareLyricDialog by viewModel.showShareLyricDialog.collectAsStateWithLifecycle()
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
    val isSupermixVisible by viewModel.isSupermixVisible.collectAsStateWithLifecycle()

    val aiState by viewModel.aiSuggestionState.collectAsStateWithLifecycle()
    val allSongs by viewModel.allSongs.collectAsStateWithLifecycle()
    val downloadedSongs by viewModel.downloadedSongs.collectAsStateWithLifecycle()
    val favoriteSongs by viewModel.favoriteSongs.collectAsStateWithLifecycle()
    val localSongs by viewModel.localSongs.collectAsStateWithLifecycle()
    val playlists by viewModel.playlists.collectAsStateWithLifecycle()
    val downloadProgress by viewModel.downloadProgress.collectAsStateWithLifecycle()

    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
    val isSearching by viewModel.isSearching.collectAsStateWithLifecycle()

    val exploreCategory by viewModel.exploreCategory.collectAsStateWithLifecycle()
    val exploreSongs by viewModel.exploreSongs.collectAsStateWithLifecycle()

    val lyrics by viewModel.parsedLyrics.collectAsStateWithLifecycle()
    val activeLyricIndex by viewModel.activeLyricIndex.collectAsStateWithLifecycle()

    var lyricQuoteToShare by remember { mutableStateOf("") }
    var showCreatePlaylistDialog by remember { mutableStateOf(false) }

    // Handle back button gracefully
    BackHandler(enabled = isPlayerExpanded || showLyrics || currentTab != "home") {
        when {
            showLyrics -> viewModel.setShowLyrics(false)
            isPlayerExpanded -> viewModel.setPlayerExpanded(false)
            currentTab != "home" -> viewModel.setTab("home")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Main Tab Content
        Box(modifier = Modifier.fillMaxSize()) {
            when (currentTab) {
                "home" -> HomeScreen(
                    aiState = aiState,
                    trendingSongs = allSongs,
                    youtubePlaylists = viewModel.youtubePlaylists,
                    youtubeChannels = viewModel.youtubeChannels,
                    playerState = playerState,
                    downloadProgress = downloadProgress,
                    isSupermixVisible = isSupermixVisible,
                    onDismissSupermix = { viewModel.dismissSupermix() },
                    onSongSelect = { song, queue -> viewModel.playSong(song, queue) },
                    onFavoriteToggle = { song -> viewModel.toggleFavorite(song) },
                    onDownloadSong = { song, quality -> viewModel.downloadSong(song, quality) },
                    onDeleteDownload = { id -> viewModel.deleteDownload(id) },
                    onOpenSettings = { viewModel.setTab("settings") },
                    onOpenSpatialDialog = { viewModel.setShowSpatialDialog(true) }
                )

                "explore" -> ExploreScreen(
                    currentCategory = exploreCategory,
                    songs = exploreSongs,
                    playerState = playerState,
                    downloadProgress = downloadProgress,
                    onCategorySelect = { cat -> viewModel.setExploreCategory(cat) },
                    onSongSelect = { song, queue -> viewModel.playSong(song, queue) },
                    onFavoriteToggle = { song -> viewModel.toggleFavorite(song) },
                    onDownloadSong = { song, quality -> viewModel.downloadSong(song, quality) },
                    onDeleteDownload = { id -> viewModel.deleteDownload(id) }
                )

                "search" -> SearchScreen(
                    query = searchQuery,
                    results = searchResults,
                    isSearching = isSearching,
                    playerState = playerState,
                    downloadProgress = downloadProgress,
                    onQueryChange = { q -> viewModel.onSearchQueryChanged(q) },
                    onSongSelect = { song, queue -> viewModel.playSong(song, queue) },
                    onFavoriteToggle = { song -> viewModel.toggleFavorite(song) },
                    onDownloadSong = { song, quality -> viewModel.downloadSong(song, quality) },
                    onDeleteDownload = { id -> viewModel.deleteDownload(id) }
                )

                "library" -> LibraryScreen(
                    favoriteSongs = favoriteSongs,
                    downloadedSongs = downloadedSongs,
                    localSongs = localSongs,
                    playlists = playlists,
                    playerState = playerState,
                    downloadProgress = downloadProgress,
                    onSongSelect = { song, queue -> viewModel.playSong(song, queue) },
                    onFavoriteToggle = { song -> viewModel.toggleFavorite(song) },
                    onDownloadSong = { song, quality -> viewModel.downloadSong(song, quality) },
                    onDeleteDownload = { id -> viewModel.deleteDownload(id) },
                    onRescanLocal = { viewModel.scanLocalMusic() },
                    onCreatePlaylistClick = { showCreatePlaylistDialog = true }
                )

                "settings" -> SettingsScreen(
                    playerState = playerState,
                    themeMode = themeMode,
                    isSupermixVisible = isSupermixVisible,
                    aiState = aiState,
                    onThemeSelect = { mode -> viewModel.setThemeMode(mode) },
                    onQualitySelect = { q -> viewModel.setAudioQuality(q) },
                    onSpatialModeSelect = { mode -> viewModel.setSpatialMode(mode) },
                    onToggleGapless = { viewModel.toggleGapless() },
                    onToggleSupermixVisible = { vis -> viewModel.setSupermixVisible(vis) },
                    onPlaySupermix = {
                        if (aiState.recommendedSongs.isNotEmpty()) {
                            viewModel.playSong(aiState.recommendedSongs.first(), aiState.recommendedSongs)
                        } else if (allSongs.isNotEmpty()) {
                            viewModel.playSong(allSongs.first(), allSongs)
                        }
                    },
                    onRescanLocal = { viewModel.scanLocalMusic() }
                )
            }
        }

        // Bottom Controls: MiniPlayer & Floating Capsule NavBar
        if (!isPlayerExpanded) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            ) {
                MiniPlayer(
                    playerState = playerState,
                    onExpandClick = { viewModel.setPlayerExpanded(true) },
                    onPlayPauseClick = { viewModel.togglePlayPause() },
                    onSkipNextClick = { viewModel.skipNext() },
                    onSpatialClick = { viewModel.setShowSpatialDialog(true) }
                )

                XMusicNavBar(
                    currentTab = currentTab,
                    onTabSelected = { tab -> viewModel.setTab(tab) }
                )
            }
        }

        // Fullscreen Now Playing Screen
        AnimatedVisibility(
            visible = isPlayerExpanded,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
        ) {
            PlayerScreen(
                playerState = playerState,
                onCollapse = { viewModel.setPlayerExpanded(false) },
                onPlayPause = { viewModel.togglePlayPause() },
                onSeekTo = { pos -> viewModel.seekTo(pos) },
                onSkipNext = { viewModel.skipNext() },
                onSkipPrevious = { viewModel.skipPrevious() },
                onToggleRepeat = { viewModel.toggleRepeat() },
                onToggleShuffle = { viewModel.toggleShuffle() },
                onToggleFavorite = { song -> viewModel.toggleFavorite(song) },
                onDownload = { song, quality -> viewModel.downloadSong(song, quality) },
                onOpenLyrics = { viewModel.setShowLyrics(true) },
                onOpenSpatialDialog = { viewModel.setShowSpatialDialog(true) },
                onOpenQualityDialog = { viewModel.setShowQualityDialog(true) }
            )
        }

        // Synchronized Karaoke Lyrics View
        AnimatedVisibility(
            visible = showLyrics,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
        ) {
            LyricsView(
                song = playerState.currentSong,
                lyrics = lyrics,
                activeLyricIndex = activeLyricIndex,
                onSeekTo = { pos -> viewModel.seekTo(pos) },
                onClose = { viewModel.setShowLyrics(false) },
                onShareQuote = { quote ->
                    lyricQuoteToShare = quote
                    viewModel.setShowShareLyricDialog(true)
                }
            )
        }

        // Modal Dialogs
        if (showQualityDialog) {
            AudioQualityDialog(
                selectedQuality = playerState.audioQuality,
                onQualitySelected = { q ->
                    viewModel.setAudioQuality(q)
                    viewModel.setShowQualityDialog(false)
                },
                onDismiss = { viewModel.setShowQualityDialog(false) }
            )
        }

        if (showSpatialDialog) {
            SpatialAudioDialog(
                currentMode = playerState.spatialMode,
                onModeSelected = { mode ->
                    viewModel.setSpatialMode(mode)
                    viewModel.setShowSpatialDialog(false)
                },
                onDismiss = { viewModel.setShowSpatialDialog(false) }
            )
        }

        if (showShareLyricDialog) {
            ShareLyricDialog(
                song = playerState.currentSong,
                lyricQuote = lyricQuoteToShare,
                onDismiss = { viewModel.setShowShareLyricDialog(false) }
            )
        }

        if (showCreatePlaylistDialog) {
            CreatePlaylistDialog(
                onDismiss = { showCreatePlaylistDialog = false },
                onConfirm = { name, desc ->
                    viewModel.createPlaylist(name, desc)
                    showCreatePlaylistDialog = false
                }
            )
        }
    }
}
