package com.example.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class Song(
    @PrimaryKey
    val id: String,
    val title: String,
    val artist: String,
    val album: String = "",
    val durationMs: Long = 0L,
    val thumbnailUrl: String = "",
    val streamUrl: String = "",
    val isDownloaded: Boolean = false,
    val localFilePath: String? = null,
    val quality: String = "HIGH", // "LOW", "MEDIUM", "HIGH" (Lossless)
    val lyrics: String? = null,    // Synced LRC format
    val isFavorite: Boolean = false,
    val playCount: Int = 0,
    val lastPlayedTime: Long = 0L,
    val source: String = "YTM",   // "YTM", "LOCAL", "FAVORITE"
    val genre: String = "Pop"
)

enum class AudioQuality(val title: String, val bitrate: String, val badge: String) {
    DATA_SAVER("Data Saver", "64 kbps Opus", "SAVER"),
    LOW("Rendah (Low)", "96 kbps AAC", "96K"),
    MEDIUM("Sedang (Medium)", "160 kbps Opus", "160K"),
    HIGH("Tinggi (High)", "320 kbps AAC", "320K"),
    LOSSLESS("Lossless Studio", "FLAC 24-bit/96kHz", "LOSSLESS")
}

enum class SpatialMode(val title: String, val description: String, val strength: Short) {
    OFF("Nonaktif (Stereo Biasa)", "Standard stereo output tanpa efek", 0),
    SPATIAL_STEREO("Spatial Stereo", "Pelebaran panggung suara lembut", 400),
    CONCERT_3D("3D Concert Hall", "Imersi ruang akustik panggung konser", 800),
    WIDE_STAGE("Ultra Wide Spatial", "Efek keliling 360° yang mendalam", 1000)
}

enum class RepeatMode {
    OFF, ALL, ONE
}

enum class AppThemeMode(val title: String) {
    SYSTEM("Mengikuti Sistem"),
    PURE_OLED("OLED Pure Black (Khusus Gelap)"),
    DARK_EXPRESSIVE("Material Expressive Dark"),
    LIGHT("Material You Terang"),
    AMBIENT_AUTO("Sensor Cahaya Otomatis (Ambient Light)")
}
