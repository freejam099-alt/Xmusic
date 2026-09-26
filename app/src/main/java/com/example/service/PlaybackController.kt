package com.example.service

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.audiofx.BassBoost
import android.media.audiofx.Virtualizer
import android.os.PowerManager
import com.example.model.AudioQuality
import com.example.model.RepeatMode
import com.example.model.Song
import com.example.model.SpatialMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

data class PlayerState(
    val currentSong: Song? = null,
    val isPlaying: Boolean = false,
    val isBuffering: Boolean = false,
    val currentPositionMs: Long = 0L,
    val durationMs: Long = 0L,
    val queue: List<Song> = emptyList(),
    val currentIndex: Int = -1,
    val spatialMode: SpatialMode = SpatialMode.SPATIAL_STEREO,
    val audioQuality: AudioQuality = AudioQuality.HIGH,
    val repeatMode: RepeatMode = RepeatMode.OFF,
    val isShuffle: Boolean = false,
    val isGaplessEnabled: Boolean = true
)

object PlaybackController {

    private val _playerState = MutableStateFlow(PlayerState())
    val playerState = _playerState.asStateFlow()

    private var mediaPlayer: MediaPlayer? = null
    private var virtualizer: Virtualizer? = null
    private var bassBoost: BassBoost? = null

    private val scope = CoroutineScope(Dispatchers.Main)
    private var progressJob: Job? = null

    var onSongChangedCallback: ((Song) -> Unit)? = null
    var onPlayStateChangedCallback: ((Boolean) -> Unit)? = null

    fun initializePlayer(context: Context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer().apply {
                setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK)
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setOnPreparedListener { mp ->
                    _playerState.value = _playerState.value.copy(
                        isBuffering = false,
                        durationMs = mp.duration.toLong()
                    )
                    mp.start()
                    _playerState.value = _playerState.value.copy(isPlaying = true)
                    startProgressTracker()
                    applyAudioEffects(mp.audioSessionId)
                    onPlayStateChangedCallback?.invoke(true)
                }
                setOnCompletionListener {
                    handleTrackCompletion()
                }
                setOnBufferingUpdateListener { _, percent ->
                    // Buffering update
                }
                setOnErrorListener { _, _, _ ->
                    _playerState.value = _playerState.value.copy(isBuffering = false, isPlaying = false)
                    false
                }
            }
        }
    }

    private fun applyAudioEffects(audioSessionId: Int) {
        try {
            // Apply Virtualizer for Spatial Audio
            virtualizer?.release()
            virtualizer = Virtualizer(0, audioSessionId).apply {
                val mode = _playerState.value.spatialMode
                if (mode != SpatialMode.OFF) {
                    enabled = true
                    setStrength(mode.strength)
                } else {
                    enabled = false
                }
            }

            // Apply subtle BassBoost for punchy richness
            bassBoost?.release()
            bassBoost = BassBoost(0, audioSessionId).apply {
                enabled = _playerState.value.spatialMode != SpatialMode.OFF
                if (enabled) {
                    setStrength(450)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setSpatialMode(mode: SpatialMode) {
        _playerState.value = _playerState.value.copy(spatialMode = mode)
        try {
            virtualizer?.let {
                if (mode == SpatialMode.OFF) {
                    it.enabled = false
                    bassBoost?.enabled = false
                } else {
                    it.enabled = true
                    it.setStrength(mode.strength)
                    bassBoost?.enabled = true
                    bassBoost?.setStrength(if (mode == SpatialMode.WIDE_STAGE) 600 else 400)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setAudioQuality(quality: AudioQuality) {
        _playerState.value = _playerState.value.copy(audioQuality = quality)
    }

    fun playSong(song: Song, newQueue: List<Song>? = null) {
        val queue = newQueue ?: _playerState.value.queue.ifEmpty { listOf(song) }
        val index = queue.indexOfFirst { it.id == song.id }.let { if (it == -1) 0 else it }

        _playerState.value = _playerState.value.copy(
            currentSong = song,
            queue = queue,
            currentIndex = index,
            isBuffering = true,
            isPlaying = false,
            currentPositionMs = 0L,
            durationMs = song.durationMs
        )

        onSongChangedCallback?.invoke(song)

        try {
            val mp = mediaPlayer ?: return
            mp.reset()

            val playUrl = if (song.isDownloaded && !song.localFilePath.isNullOrBlank()) {
                song.localFilePath
            } else {
                song.streamUrl
            }

            mp.setDataSource(playUrl)
            mp.prepareAsync()
        } catch (e: Exception) {
            e.printStackTrace()
            _playerState.value = _playerState.value.copy(isBuffering = false, isPlaying = false)
        }
    }

    fun togglePlayPause() {
        val mp = mediaPlayer ?: return
        if (mp.isPlaying) {
            mp.pause()
            _playerState.value = _playerState.value.copy(isPlaying = false)
            stopProgressTracker()
            onPlayStateChangedCallback?.invoke(false)
        } else {
            if (_playerState.value.currentSong != null) {
                mp.start()
                _playerState.value = _playerState.value.copy(isPlaying = true)
                startProgressTracker()
                onPlayStateChangedCallback?.invoke(true)
            }
        }
    }

    fun seekTo(positionMs: Long) {
        val mp = mediaPlayer ?: return
        try {
            mp.seekTo(positionMs.toInt())
            _playerState.value = _playerState.value.copy(currentPositionMs = positionMs)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun skipNext() {
        val state = _playerState.value
        if (state.queue.isEmpty()) return

        val nextIndex = when {
            state.isShuffle -> (state.queue.indices).random()
            state.currentIndex < state.queue.size - 1 -> state.currentIndex + 1
            state.repeatMode == RepeatMode.ALL -> 0
            else -> return
        }

        val nextSong = state.queue[nextIndex]
        playSong(nextSong, state.queue)
    }

    fun skipPrevious() {
        val state = _playerState.value
        if (state.queue.isEmpty()) return

        // If played more than 3 seconds, rewind to start
        if (state.currentPositionMs > 3000) {
            seekTo(0)
            return
        }

        val prevIndex = when {
            state.currentIndex > 0 -> state.currentIndex - 1
            state.repeatMode == RepeatMode.ALL -> state.queue.size - 1
            else -> 0
        }

        val prevSong = state.queue[prevIndex]
        playSong(prevSong, state.queue)
    }

    fun toggleRepeat() {
        val current = _playerState.value.repeatMode
        val next = when (current) {
            RepeatMode.OFF -> RepeatMode.ALL
            RepeatMode.ALL -> RepeatMode.ONE
            RepeatMode.ONE -> RepeatMode.OFF
        }
        _playerState.value = _playerState.value.copy(repeatMode = next)
    }

    fun toggleShuffle() {
        _playerState.value = _playerState.value.copy(isShuffle = !_playerState.value.isShuffle)
    }

    fun toggleGapless() {
        _playerState.value = _playerState.value.copy(isGaplessEnabled = !_playerState.value.isGaplessEnabled)
    }

    private fun handleTrackCompletion() {
        val state = _playerState.value
        when (state.repeatMode) {
            RepeatMode.ONE -> {
                seekTo(0)
                mediaPlayer?.start()
                _playerState.value = _playerState.value.copy(isPlaying = true)
            }
            RepeatMode.ALL, RepeatMode.OFF -> {
                if (state.currentIndex < state.queue.size - 1 || state.repeatMode == RepeatMode.ALL) {
                    skipNext()
                } else {
                    _playerState.value = _playerState.value.copy(isPlaying = false, currentPositionMs = 0L)
                    stopProgressTracker()
                    onPlayStateChangedCallback?.invoke(false)
                }
            }
        }
    }

    private fun startProgressTracker() {
        stopProgressTracker()
        progressJob = scope.launch {
            while (isActive) {
                mediaPlayer?.let { mp ->
                    if (mp.isPlaying) {
                        _playerState.value = _playerState.value.copy(
                            currentPositionMs = mp.currentPosition.toLong(),
                            durationMs = mp.duration.toLong().coerceAtLeast(_playerState.value.durationMs)
                        )
                    }
                }
                delay(400)
            }
        }
    }

    private fun stopProgressTracker() {
        progressJob?.cancel()
        progressJob = null
    }

    fun release() {
        stopProgressTracker()
        try {
            virtualizer?.release()
            bassBoost?.release()
            mediaPlayer?.release()
        } catch (_: Exception) {}
        mediaPlayer = null
        virtualizer = null
        bassBoost = null
    }
}
