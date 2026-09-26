package com.example.data.download

import android.content.Context
import com.example.data.local.SongDao
import com.example.model.AudioQuality
import com.example.model.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

class MusicDownloadManager(
    private val context: Context,
    private val songDao: SongDao
) {
    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    // Map of songId -> progress (0..100) or -1 for error
    private val _downloadProgressMap = MutableStateFlow<Map<String, Int>>(emptyMap())
    val downloadProgressMap = _downloadProgressMap.asStateFlow()

    private val downloadsDir: File
        get() = File(context.filesDir, "xmusic_downloads").apply { if (!exists()) mkdirs() }

    suspend fun downloadSong(song: Song, quality: AudioQuality = AudioQuality.HIGH): Boolean = withContext(Dispatchers.IO) {
        val songId = song.id
        updateProgress(songId, 5)

        val targetFile = File(downloadsDir, "song_${songId.replace(Regex("[^a-zA-Z0-9_]"), "_")}.mp3")

        try {
            // First check if already downloaded
            if (targetFile.exists() && targetFile.length() > 50000) {
                songDao.setDownloaded(songId, true, targetFile.absolutePath, quality.badge)
                updateProgress(songId, 100)
                return@withContext true
            }

            val request = Request.Builder()
                .url(song.streamUrl)
                .header("User-Agent", "XMusic Android Downloader")
                .build()

            updateProgress(songId, 20)
            val response = httpClient.newCall(request).execute()

            if (!response.isSuccessful || response.body == null) {
                updateProgress(songId, -1)
                return@withContext false
            }

            val body = response.body!!
            val contentLength = body.contentLength()
            val inputStream = body.byteStream()
            val outputStream = FileOutputStream(targetFile)

            val buffer = ByteArray(8 * 1024)
            var bytesRead: Int
            var totalBytesRead: Long = 0

            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
                totalBytesRead += bytesRead
                if (contentLength > 0) {
                    val progress = (20 + (totalBytesRead * 75 / contentLength)).toInt()
                    updateProgress(songId, progress.coerceIn(20, 95))
                }
            }

            outputStream.flush()
            outputStream.close()
            inputStream.close()

            updateProgress(songId, 100)

            // Save or update in Room
            val updatedSong = song.copy(
                isDownloaded = true,
                localFilePath = targetFile.absolutePath,
                quality = quality.badge
            )
            songDao.insertSong(updatedSong)

            return@withContext true
        } catch (e: Exception) {
            e.printStackTrace()
            updateProgress(songId, -1)
            return@withContext false
        }
    }

    suspend fun removeDownload(songId: String) = withContext(Dispatchers.IO) {
        val song = songDao.getSongById(songId)
        if (song?.localFilePath != null) {
            val file = File(song.localFilePath)
            if (file.exists()) file.delete()
        }
        songDao.setDownloaded(songId, false, null, "HIGH")
        val current = _downloadProgressMap.value.toMutableMap()
        current.remove(songId)
        _downloadProgressMap.value = current
    }

    private fun updateProgress(songId: String, progress: Int) {
        val current = _downloadProgressMap.value.toMutableMap()
        if (progress >= 100 || progress < 0) {
            current[songId] = progress
        } else {
            current[songId] = progress
        }
        _downloadProgressMap.value = current
    }
}
