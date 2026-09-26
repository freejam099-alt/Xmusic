package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.download.MusicDownloadManager
import com.example.data.local.LocalMusicScanner
import com.example.data.local.XMusicDatabase
import com.example.data.remote.YtMusicClient
import com.example.data.repository.MusicRepository
import com.example.model.AppThemeMode
import com.example.model.AudioQuality
import com.example.model.LyricLine
import com.example.model.LrcParser
import com.example.model.Playlist
import com.example.model.Song
import com.example.model.SpatialMode
import com.example.service.MusicPlaybackService
import com.example.service.PlaybackController
import com.example.service.PlayerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

data class AiSuggestionState(
    val greeting: String = "Selamat Datang di XMusic",
    val moodTitle: String = "Campuran Supermix AI",
    val moodSubtitle: String = "Disesuaikan otomatis dengan ritme & lagu yang sering Anda dengar",
    val recommendedSongs: List<Song> = emptyList(),
    val quickPicks: List<Song> = emptyList(),
    val listeningInsights: String = "Menikmati audio Lossless dan efek Spatial aktif"
)

class MusicViewModel(application: Application) : AndroidViewModel(application) {

    private val db = XMusicDatabase.getInstance(application)
    private val localScanner = LocalMusicScanner(application)
    private val ytClient = YtMusicClient(application)
    private val downloadManager = MusicDownloadManager(application, db.songDao())
    val repository = MusicRepository(
        context = application,
        songDao = db.songDao(),
        playlistDao = db.playlistDao(),
        localScanner = localScanner,
        ytClient = ytClient,
        downloadManager = downloadManager
    )

    val youtubePlaylists = repository.getYouTubePlaylists()
    val youtubeChannels = repository.getYouTubeChannels()

    private val _youtubeApiKey = MutableStateFlow(repository.getApiKey())
    val youtubeApiKey = _youtubeApiKey.asStateFlow()

    fun updateYouTubeApiKey(newKey: String) {
        repository.setApiKey(newKey)
        _youtubeApiKey.value = newKey
        refreshYouTubeData()
    }

    fun refreshYouTubeData() {
        viewModelScope.launch {
            val trending = repository.getTrendingSongs()
            _exploreSongs.value = trending
        }
    }

    // Player State
    val playerState: StateFlow<PlayerState> = PlaybackController.playerState

    // Navigation & Screen selection
    private val _currentTab = MutableStateFlow("home") // home, explore, library, search, settings
    val currentTab = _currentTab.asStateFlow()

    private val _isPlayerExpanded = MutableStateFlow(false)
    val isPlayerExpanded = _isPlayerExpanded.asStateFlow()

    private val _showLyricsSheet = MutableStateFlow(false)
    val showLyricsSheet = _showLyricsSheet.asStateFlow()

    private val _showSpatialDialog = MutableStateFlow(false)
    val showSpatialDialog = _showSpatialDialog.asStateFlow()

    private val _showQualityDialog = MutableStateFlow(false)
    val showQualityDialog = _showQualityDialog.asStateFlow()

    private val _showShareLyricDialog = MutableStateFlow(false)
    val showShareLyricDialog = _showShareLyricDialog.asStateFlow()

    private val _themeMode = MutableStateFlow(AppThemeMode.PURE_OLED)
    val themeMode = _themeMode.asStateFlow()

    // Library flows
    val allSongs: StateFlow<List<Song>> = repository.allSongs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val downloadedSongs: StateFlow<List<Song>> = repository.downloadedSongs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteSongs: StateFlow<List<Song>> = repository.favoriteSongs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val localSongs: StateFlow<List<Song>> = repository.localSongs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val topPlayedSongs: StateFlow<List<Song>> = repository.topPlayedSongs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val playlists: StateFlow<List<Playlist>> = repository.playlists
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val downloadProgress: StateFlow<Map<String, Int>> = repository.downloadProgress

    // Search state
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Song>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    // Trending & Explore
    private val _exploreCategory = MutableStateFlow("Semua")
    val exploreCategory = _exploreCategory.asStateFlow()

    private val _exploreSongs = MutableStateFlow<List<Song>>(emptyList())
    val exploreSongs = _exploreSongs.asStateFlow()

    // Lyrics State
    private val _parsedLyrics = MutableStateFlow<List<LyricLine>>(emptyList())
    val parsedLyrics = _parsedLyrics.asStateFlow()

    private val _activeLyricIndex = MutableStateFlow(-1)
    val activeLyricIndex = _activeLyricIndex.asStateFlow()

    // AI Suggestions
    private val _aiSuggestionState = MutableStateFlow(AiSuggestionState())
    val aiSuggestionState = _aiSuggestionState.asStateFlow()

    private val prefs = application.getSharedPreferences("xmusic_prefs", android.content.Context.MODE_PRIVATE)
    private val _isSupermixVisible = MutableStateFlow(prefs.getBoolean("show_supermix_capsule", true))
    val isSupermixVisible = _isSupermixVisible.asStateFlow()

    fun dismissSupermix() {
        _isSupermixVisible.value = false
        prefs.edit().putBoolean("show_supermix_capsule", false).apply()
    }

    fun setSupermixVisible(visible: Boolean) {
        _isSupermixVisible.value = visible
        prefs.edit().putBoolean("show_supermix_capsule", visible).apply()
    }

    private var searchDebounceJob: Job? = null

    init {
        MusicPlaybackService.startService(application)
        loadInitialExplore()
        scanLocalMusic()

        // Observe player position for synchronized lyrics
        viewModelScope.launch {
            playerState.collect { state ->
                val currentMs = state.currentPositionMs
                val lines = _parsedLyrics.value
                if (lines.isNotEmpty()) {
                    var idx = -1
                    for (i in lines.indices) {
                        if (lines[i].timeMs <= currentMs) {
                            idx = i
                        } else {
                            break
                        }
                    }
                    if (idx != _activeLyricIndex.value) {
                        _activeLyricIndex.value = idx
                    }
                }
            }
        }

        // Observe song changes to load lyrics
        viewModelScope.launch {
            playerState.collect { state ->
                val song = state.currentSong
                if (song != null) {
                    val rawLyrics = song.lyrics ?: repository.fetchLyrics(song.artist, song.title)
                    _parsedLyrics.value = LrcParser.parse(rawLyrics)
                } else {
                    _parsedLyrics.value = emptyList()
                }
            }
        }

        // Compute AI Suggestions based on listening history & time of day
        viewModelScope.launch {
            combine(allSongs, topPlayedSongs) { all, top ->
                computeAiSuggestions(all, top)
            }.collect { suggestions ->
                _aiSuggestionState.value = suggestions
            }
        }
    }

    private fun computeAiSuggestions(all: List<Song>, top: List<Song>): AiSuggestionState {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val greeting = when (hour) {
            in 4..11 -> "Selamat Pagi, Penikmat Musik"
            in 12..16 -> "Selamat Siang, Waktunya Fokus"
            in 17..20 -> "Selamat Sore, Bersantai Bersama"
            else -> "Malam yang Tenang bersama XMusic"
        }

        val moodTitle = when (hour) {
            in 4..11 -> "Supermix Penyemangat Pagi"
            in 12..16 -> "Alunan Fokus & Produktif"
            in 17..20 -> "Mix Pulang Kerja & Senja"
            else -> "Lo-Fi & Akustik Larut Malam"
        }

        val recommended = if (top.isNotEmpty()) {
            top.take(6)
        } else {
            all.take(6)
        }

        val quickPicks = all.shuffled().take(6)

        return AiSuggestionState(
            greeting = greeting,
            moodTitle = moodTitle,
            moodSubtitle = "Rekomendasi AI pintar berdasarkan frekuensi putar Anda",
            recommendedSongs = recommended,
            quickPicks = quickPicks,
            listeningInsights = "Analisis: Favorit genre Anda adalah ${top.firstOrNull()?.genre ?: "Pop/Indonesian Hits"}"
        )
    }

    private fun loadInitialExplore() {
        viewModelScope.launch {
            val trending = repository.getTrendingSongs()
            _exploreSongs.value = trending
        }
    }

    fun setExploreCategory(category: String) {
        _exploreCategory.value = category
        viewModelScope.launch {
            _exploreSongs.value = repository.getSongsByGenre(category)
        }
    }

    fun setTab(tab: String) {
        _currentTab.value = tab
    }

    fun setPlayerExpanded(expanded: Boolean) {
        _isPlayerExpanded.value = expanded
    }

    fun setShowLyrics(show: Boolean) {
        _showLyricsSheet.value = show
    }

    fun setShowSpatialDialog(show: Boolean) {
        _showSpatialDialog.value = show
    }

    fun setShowQualityDialog(show: Boolean) {
        _showQualityDialog.value = show
    }

    fun setShowShareLyricDialog(show: Boolean) {
        _showShareLyricDialog.value = show
    }

    fun setThemeMode(mode: AppThemeMode) {
        _themeMode.value = mode
    }

    fun playSong(song: Song, queue: List<Song>? = null) {
        PlaybackController.playSong(song, queue)
        viewModelScope.launch {
            repository.recordPlay(song)
        }
    }

    fun togglePlayPause() {
        PlaybackController.togglePlayPause()
    }

    fun seekTo(positionMs: Long) {
        PlaybackController.seekTo(positionMs)
    }

    fun skipNext() {
        PlaybackController.skipNext()
    }

    fun skipPrevious() {
        PlaybackController.skipPrevious()
    }

    fun toggleRepeat() {
        PlaybackController.toggleRepeat()
    }

    fun toggleShuffle() {
        PlaybackController.toggleShuffle()
    }

    fun toggleGapless() {
        PlaybackController.toggleGapless()
    }

    fun setSpatialMode(mode: SpatialMode) {
        PlaybackController.setSpatialMode(mode)
    }

    fun setAudioQuality(quality: AudioQuality) {
        PlaybackController.setAudioQuality(quality)
    }

    fun toggleFavorite(song: Song) {
        viewModelScope.launch {
            repository.toggleFavorite(song)
            if (playerState.value.currentSong?.id == song.id) {
                // Keep local state in sync
                PlaybackController.playerState.value.currentSong?.let {
                    // Update reference
                }
            }
        }
    }

    fun downloadSong(song: Song, quality: AudioQuality = AudioQuality.HIGH) {
        viewModelScope.launch {
            repository.downloadSong(song, quality)
        }
    }

    fun deleteDownload(songId: String) {
        viewModelScope.launch {
            repository.removeDownload(songId)
        }
    }

    fun scanLocalMusic() {
        viewModelScope.launch {
            repository.scanLocalTracks()
        }
    }

    fun createPlaylist(name: String, desc: String = "") {
        viewModelScope.launch {
            repository.createPlaylist(name, desc)
        }
    }

    fun addSongToPlaylist(playlistId: Long, song: Song) {
        viewModelScope.launch {
            repository.addSongToPlaylist(playlistId, song)
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        searchDebounceJob?.cancel()
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            _isSearching.value = false
            return
        }
        _isSearching.value = true
        searchDebounceJob = viewModelScope.launch {
            delay(280) // Live Write debounce
            val results = repository.search(query)
            _searchResults.value = results
            _isSearching.value = false
        }
    }
}
