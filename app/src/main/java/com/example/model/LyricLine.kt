package com.example.model

data class LyricLine(
    val timeMs: Long,
    val text: String
)

object LrcParser {
    // Regex matches [mm:ss.xx] or [mm:ss]
    private val LRC_REGEX = Regex("\\[(\\d{2}):(\\d{2})(?:\\.(\\d{1,3}))?\\](.*)")

    fun parse(lrcContent: String?): List<LyricLine> {
        if (lrcContent.isNullOrBlank()) return emptyList()
        val lines = mutableListOf<LyricLine>()

        lrcContent.lineSequence().forEach { line ->
            val match = LRC_REGEX.find(line.trim())
            if (match != null) {
                val min = match.groupValues[1].toLongOrNull() ?: 0L
                val sec = match.groupValues[2].toLongOrNull() ?: 0L
                val millisString = match.groupValues[3]
                val millis = when (millisString.length) {
                    1 -> millisString.toLong() * 100
                    2 -> millisString.toLong() * 10
                    3 -> millisString.toLong()
                    else -> 0L
                }
                val timeMs = (min * 60 + sec) * 1000 + millis
                val text = match.groupValues[4].trim()
                if (text.isNotEmpty()) {
                    lines.add(LyricLine(timeMs = timeMs, text = text))
                }
            } else if (line.isNotBlank() && !line.startsWith("[")) {
                // If it's plain text without timestamps, synthesize spaced lines
                lines.add(LyricLine(timeMs = lines.size * 4000L, text = line.trim()))
            }
        }

        return lines.sortedBy { it.timeMs }
    }
}
