package parser

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

class SongParserTest {

    @Test
    fun `parser reads song header correctly`() {
        val file = File.createTempFile(
            "test_song",
            ".txt"
        )

        file.writeText(
            """
            44100 4 120
            sin|C4 1
            """.trimIndent()
        )

        val song = SongParser().parse(file.path)

        assertEquals(
            44100,
            song.header.sampleRate
        )

        assertEquals(
            4,
            song.header.beatsPerMeasure
        )

        assertEquals(
            120,
            song.header.tempo
        )

        file.delete()
    }


    @Test
    fun `parser reads channel waveform`() {
        val file = File.createTempFile(
            "test_song",
            ".txt"
        )

        file.writeText(
            """
            44100 4 120
            square|C4 1
            """.trimIndent()
        )

        val song = SongParser().parse(file.path)

        assertEquals(
            "square",
            song.channels[0].waveform
        )

        file.delete()
    }


    @Test
    fun `parser reads effects`() {
        val file = File.createTempFile(
            "test_song",
            ".txt"
        )

        file.writeText(
            """
            44100 4 120
            sin vol$.5 ads$.01$.2$.1|C4 1
            """.trimIndent()
        )

        val song = SongParser().parse(file.path)

        assertEquals(
            listOf(
                "vol$.5",
                "ads$.01$.2$.1"
            ),
            song.channels[0].effects
        )

        file.delete()
    }


    @Test
    fun `parser reads notes and durations`() {
        val file = File.createTempFile(
            "test_song",
            ".txt"
        )

        file.writeText(
            """
            44100 4 120
            sin|C4 1 D4 .5
            """.trimIndent()
        )

        val song = SongParser().parse(file.path)

        val notes =
            song.channels[0].notes

        assertEquals(
            2,
            notes.size
        )

        assertEquals(
            "C4",
            notes[0].pitch
        )

        assertEquals(
            0.5,
            notes[1].duration
        )

        file.delete()
    }


    @Test
    fun `parser reads rests`() {
        val file = File.createTempFile(
            "test_song",
            ".txt"
        )

        file.writeText(
            """
            44100 4 120
            sin|- 1
            """.trimIndent()
        )

        val song = SongParser().parse(file.path)

        assertEquals(
            "-",
            song.channels[0].notes[0].pitch
        )

        file.delete()
    }


    @Test
    fun `parser rejects invalid header`() {
        val file = File.createTempFile(
            "test_song",
            ".txt"
        )

        file.writeText(
            """
            invalid header
            """.trimIndent()
        )

        assertThrows(
            IllegalArgumentException::class.java
        ) {
            SongParser().parse(file.path)
        }

        file.delete()
    }


    @Test
    fun `parser reads multiple channels`() {
        val file = File.createTempFile(
            "test_song",
            ".txt"
        )

        file.writeText(
            """
            44100 4 120
            sin|C4 1
            square|C3 1
            whitenoise|C1 1
            """.trimIndent()
        )

        val song = SongParser().parse(file.path)

        assertEquals(
            3,
            song.channels.size
        )

        assertEquals(
            "sin",
            song.channels[0].waveform
        )

        assertEquals(
            "square",
            song.channels[1].waveform
        )

        assertEquals(
            "whitenoise",
            song.channels[2].waveform
        )

        file.delete()
    }
}