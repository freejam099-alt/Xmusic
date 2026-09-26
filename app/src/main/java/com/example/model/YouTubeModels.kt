package com.example.model

data class YouTubeChannel(
    val id: String,
    val title: String,
    val thumbnailUrl: String,
    val subscriberCount: String = ""
)

data class YouTubePlaylist(
    val id: String,
    val title: String,
    val channelTitle: String,
    val thumbnailUrl: String,
    val itemCount: Int = 0
)
