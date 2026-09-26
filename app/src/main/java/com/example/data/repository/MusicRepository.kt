package com.example.data.repository

import android.content.Context
import com.example.data.download.MusicDownloadManager
import com.example.data.local.LocalMusicScanner
import com.example.data.local.PlaylistDao
import com.example.data.local.SongDao
import com.example.data.remote.YtMusicClient
import com.example.model.AudioQuality
import com.example.model.Playlist
import com.example.model.PlaylistSongCrossRef
import com.example.model.Song
import com.example.model.YouTubeChannel
import com.example.model.YouTubePlaylist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MusicRepository(
    private val context: Context,
    private val songDao: SongDao,
    private val playlistDao: PlaylistDao,
    private val localScanner: LocalMusicScanner,
    private val ytClient: YtMusicClient,
    private val downloadManager: MusicDownloadManager
) {
    val allSongs: Flow<List<Song>> = songDao.getAllSongs()
    val downloadedSongs: Flow<List<Song>> = songDao.getDownloadedSongs()
    val favoriteSongs: Flow<List<Song>> = songDao.getFavoriteSongs()
    val localSongs: Flow<List<Song>> = songDao.getLocalSongs()
    val topPlayedSongs: Flow<List<Song>> = songDao.getTopPlayedSongs()
    val playlists: Flow<List<Playlist>> = playlistDao.getAllPlaylists()
    val downloadProgress = downloadManager.downloadProgressMap

    init {
        // Pre-seed official YouTube catalog into database
        CoroutineScope(Dispatchers.IO).launch {
            val existing = songDao.getAllSongs().firstOrNull()
            if (existing.isNullOrEmpty()) {
                val seedSongs = ytClient.getTrendingSongs()
                songDao.insertSongs(seedSongs)
                playlistDao.insertPlaylist(
                    Playlist(
                        name = "Favorit Saya",
                        description = "Koleksi lagu paling sering didengar di XMusic",
                        songCount = 0
                    )
                )
                playlistDao.insertPlaylist(
                    Playlist(
                        name = "YouTube Music Top Hits",
                        description = "Daftar putar resmi dari YouTube Data API v3",
                        songCount = seedSongs.size
                    )
                )
            }
        }
    }

    fun getApiKey(): String = ytClient.getApiKey()

    fun setApiKey(key: String) {
        ytClient.setApiKey(key)
    }

    suspend fun getTrendingSongs(): List<Song> = withContext(Dispatchers.IO) {
        val trending = ytClient.getTrendingSongs()
        songDao.insertSongs(trending)
        trending
    }

    suspend fun getSongsByGenre(genre: String): List<Song> = withContext(Dispatchers.IO) {
        ytClient.getSongsByGenre(genre)
    }

    suspend fun search(query: String): List<Song> = withContext(Dispatchers.IO) {
        ytClient.searchSongs(query)
    }

    fun getYouTubePlaylists(): List<YouTubePlaylist> = ytClient.authenticPlaylists

    fun getYouTubeChannels(): List<YouTubeChannel> = ytClient.authenticChannels

    suspend fun scanLocalTracks(): List<Song> = withContext(Dispatchers.IO) {
        val scanned = localScanner.scanLocalTracks()
        if (scanned.isNotEmpty()) {
            songDao.insertSongs(scanned)
        }
        scanned
    }

    suspend fun toggleFavorite(song: Song) = withContext(Dispatchers.IO) {
        val newFav = !song.isFavorite
        songDao.insertSong(song.copy(isFavorite = newFav))
        songDao.setFavorite(song.id, newFav)
    }

    suspend fun downloadSong(song: Song, quality: AudioQuality): Boolean {
        return downloadManager.downloadSong(song, quality)
    }

    suspend fun removeDownload(songId: String) {
        downloadManager.removeDownload(songId)
    }

    suspend fun recordPlay(song: Song) = withContext(Dispatchers.IO) {
        songDao.insertSong(song)
        songDao.incrementPlayCount(song.id, System.currentTimeMillis())
    }

    suspend fun createPlaylist(name: String, description: String = ""): Long = withContext(Dispatchers.IO) {
        playlistDao.insertPlaylist(
            Playlist(
                name = name,
                description = description,
                songCount = 0
            )
        )
    }

    suspend fun addSongToPlaylist(playlistId: Long, song: Song) = withContext(Dispatchers.IO) {
        songDao.insertSong(song)
        playlistDao.addSongToPlaylist(PlaylistSongCrossRef(playlistId, song.id))
        playlistDao.updateSongCount(playlistId)
    }

    fun getSongsForPlaylist(playlistId: Long): Flow<List<Song>> {
        return playlistDao.getSongsForPlaylist(playlistId)
    }

    suspend fun fetchLyrics(artist: String, title: String): String? {
        return ytClient.fetchSyncedLyrics(artist, title)
    }
}
