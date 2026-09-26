package com.example

import com.example.model.LrcParser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class XMusicUnitTest {

    @Test
    fun testLrcParserWithStandardTimestamps() {
        val lrc = """
            [00:12.50]Hello world
            [01:05.20]Second line of song
        """.trimIndent()

        val parsed = LrcParser.parse(lrc)
        assertEquals(2, parsed.size)
        assertEquals(12500L, parsed[0].timeMs)
        assertEquals("Hello world", parsed[0].text)
        assertEquals(65200L, parsed[1].timeMs)
        assertEquals("Second line of song", parsed[1].text)
    }

    @Test
    fun testLrcParserEmpty() {
        val parsed = LrcParser.parse(null)
        assertTrue(parsed.isEmpty())
    }
}
