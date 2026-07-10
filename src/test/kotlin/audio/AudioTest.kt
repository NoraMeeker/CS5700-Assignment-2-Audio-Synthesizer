package audio

import model.Channel
import model.Note
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AudioTest {

    @Test
    fun `mixer combines multiple channels`() {
        val mixer = Mixer()

        val result =
            mixer.mix(
                listOf(
                    doubleArrayOf(
                        0.2,
                        0.3
                    ),
                    doubleArrayOf(
                        0.1,
                        0.2
                    )
                )
            )

        assertArrayEquals(
            doubleArrayOf(
                0.3,
                0.5
            ),
            result,
            0.000001
        )
    }


    @Test
    fun `mixer supports channels of different lengths`() {
        val mixer = Mixer()

        val result =
            mixer.mix(
                listOf(
                    doubleArrayOf(
                        0.5,
                        0.5,
                        0.5
                    ),
                    doubleArrayOf(
                        0.2
                    )
                )
            )

        assertArrayEquals(
            doubleArrayOf(
                0.7,
                0.5,
                0.5
            ),
            result,
            0.000001
        )
    }


    @Test
    fun `mixer returns empty array when no channels exist`() {
        val mixer = Mixer()

        val result =
            mixer.mix(
                emptyList()
            )

        assertEquals(
            0,
            result.size
        )
    }


    @Test
    fun `synthesizer generates samples for a note`() {
        val synthesizer =
            Synthesizer()

        val channel =
            Channel(
                waveform = "sin",
                effects = emptyList(),
                notes = listOf(
                    Note(
                        "A4",
                        1.0
                    )
                )
            )

        val samples =
            synthesizer.synthesizeChannel(
                channel,
                44100,
                60
            )

        assertTrue(
            samples.isNotEmpty()
        )
    }


    @Test
    fun `synthesizer creates correct sample length`() {
        val synthesizer =
            Synthesizer()

        val channel =
            Channel(
                waveform = "sin",
                effects = emptyList(),
                notes = listOf(
                    Note(
                        "A4",
                        1.0
                    )
                )
            )

        val samples =
            synthesizer.synthesizeChannel(
                channel,
                44100,
                60
            )

        assertEquals(
            44100,
            samples.size
        )
    }


    @Test
    fun `rest note produces silence`() {
        val synthesizer =
            Synthesizer()

        val channel =
            Channel(
                waveform = "sin",
                effects = emptyList(),
                notes = listOf(
                    Note(
                        "-",
                        1.0
                    )
                )
            )

        val samples =
            synthesizer.synthesizeChannel(
                channel,
                44100,
                60
            )

        assertTrue(
            samples.all {
                it == 0.0
            }
        )
    }


    @Test
    fun `synthesizer applies channel volume effect`() {
        val synthesizer =
            Synthesizer()

        val channel =
            Channel(
                waveform = "sin",
                effects = listOf(
                    "vol$.1"
                ),
                notes = listOf(
                    Note(
                        "A4",
                        1.0
                    )
                )
            )

        val samples =
            synthesizer.synthesizeChannel(
                channel,
                44100,
                60
            )

        assertTrue(
            samples.max() <= 0.1
        )
    }
}