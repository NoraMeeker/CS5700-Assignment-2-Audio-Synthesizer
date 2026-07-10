package waveform

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class WaveformTest {

    @Test
    fun `sine wave produces value in valid range`() {
        val waveform = SineWave()

        val sample =
            waveform.sample(
                440.0,
                0.001
            )

        assertTrue(
            sample >= -1.0 &&
                    sample <= 1.0
        )
    }


    @Test
    fun `sine wave starts at zero`() {
        val waveform = SineWave()

        val sample =
            waveform.sample(
                440.0,
                0.0
            )

        assertEquals(
            0.0,
            sample,
            0.0001
        )
    }


    @Test
    fun `square wave only produces positive or negative one`() {
        val waveform = SquareWave()

        val sample =
            waveform.sample(
                440.0,
                0.001
            )

        assertTrue(
            sample == 1.0 ||
                    sample == -1.0
        )
    }


    @Test
    fun `square wave changes sign over time`() {
        val waveform = SquareWave()

        val first =
            waveform.sample(
                440.0,
                0.0
            )

        val second =
            waveform.sample(
                440.0,
                0.002
            )

        assertNotEquals(
            first,
            second
        )
    }


    @Test
    fun `saw wave produces value in valid range`() {
        val waveform = SawWave()

        repeat(100) {
            val sample =
                waveform.sample(
                    440.0,
                    it.toDouble() / 1000
                )

            assertTrue(
                sample >= -1.0 &&
                        sample <= 1.0
            )
        }
    }


    @Test
    fun `white noise produces random values in valid range`() {
        val waveform = WhiteNoise()

        repeat(100) {
            val sample =
                waveform.sample(
                    440.0,
                    0.001
                )

            assertTrue(
                sample >= -1.0 &&
                        sample <= 1.0
            )
        }
    }


    @Test
    fun `waveform factory creates sine waveform`() {
        val factory = WaveformFactory()

        assertTrue(
            factory.create("sin")
                    is SineWave
        )
    }


    @Test
    fun `waveform factory creates square waveform`() {
        val factory = WaveformFactory()

        assertTrue(
            factory.create("square")
                    is SquareWave
        )
    }


    @Test
    fun `waveform factory creates saw waveform`() {
        val factory = WaveformFactory()

        assertTrue(
            factory.create("saw")
                    is SawWave
        )
    }


    @Test
    fun `waveform factory creates white noise waveform`() {
        val factory = WaveformFactory()

        assertTrue(
            factory.create("whitenoise")
                    is WhiteNoise
        )
    }


    @Test
    fun `waveform factory rejects unknown waveform`() {
        val factory = WaveformFactory()

        assertThrows(
            IllegalArgumentException::class.java
        ) {
            factory.create("triangle")
        }
    }
}