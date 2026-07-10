package effects

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class EffectsTest {

    @Test
    fun `raw audio returns unchanged samples`() {
        val effect = RawAudio()

        val input =
            doubleArrayOf(
                0.5,
                -0.5,
                1.0
            )

        val output =
            effect.apply(input)

        assertArrayEquals(
            input,
            output
        )
    }


    @Test
    fun `volume effect scales samples`() {
        val effect =
            VolumeEffect(
                RawAudio(),
                0.5
            )

        val output =
            effect.apply(
                doubleArrayOf(
                    1.0,
                    -1.0,
                    0.5
                )
            )

        assertArrayEquals(
            doubleArrayOf(
                0.5,
                -0.5,
                0.25
            ),
            output
        )
    }


    @Test
    fun `volume effect with one keeps samples unchanged`() {
        val effect =
            VolumeEffect(
                RawAudio(),
                1.0
            )

        val input =
            doubleArrayOf(
                0.3,
                -0.7
            )

        val output =
            effect.apply(input)

        assertArrayEquals(
            input,
            output
        )
    }


    @Test
    fun `clip effect limits values above threshold`() {
        val effect =
            ClipEffect(
                RawAudio(),
                0.5
            )

        val output =
            effect.apply(
                doubleArrayOf(
                    1.0,
                    -1.0,
                    0.25
                )
            )

        assertArrayEquals(
            doubleArrayOf(
                0.5,
                -0.5,
                0.25
            ),
            output
        )
    }


    @Test
    fun `clip effect leaves values inside threshold unchanged`() {
        val effect =
            ClipEffect(
                RawAudio(),
                0.8
            )

        val output =
            effect.apply(
                doubleArrayOf(
                    0.5,
                    -0.5
                )
            )

        assertArrayEquals(
            doubleArrayOf(
                0.5,
                -0.5
            ),
            output
        )
    }


    @Test
    fun `tanh distortion changes large signals`() {
        val effect =
            TanhEffect(
                RawAudio(),
                5.0
            )

        val output =
            effect.apply(
                doubleArrayOf(
                    1.0
                )
            )

        assertTrue(
            output[0] < 1.0
        )
    }


    @Test
    fun `tanh distortion keeps values in range`() {
        val effect =
            TanhEffect(
                RawAudio(),
                10.0
            )

        val output =
            effect.apply(
                doubleArrayOf(
                    10.0,
                    -10.0
                )
            )

        assertTrue(
            output[0] <= 1.0
        )

        assertTrue(
            output[0] >= -1.0
        )

        assertTrue(
            output[1] <= 1.0
        )

        assertTrue(
            output[1] >= -1.0
        )
    }


    @Test
    fun `ADS effect applies sustain level`() {
        val effect =
            ADSEffect(
                RawAudio(),
                0.01,
                0.2,
                0.1,
                44100
            )

        val input =
            DoubleArray(44100) {
                1.0
            }

        val output =
            effect.apply(input)

        assertEquals(
            0.1,
            output.last(),
            0.01
        )
    }


    @Test
    fun `ADS effect starts quiet and increases volume`() {
        val effect =
            ADSEffect(
                RawAudio(),
                0.01,
                0.2,
                0.1,
                44100
            )

        val input =
            DoubleArray(1000) {
                1.0
            }

        val output =
            effect.apply(input)

        assertTrue(
            output[0] < output[500]
        )
    }


    @Test
    fun `effect factory creates volume decorator`() {
        val factory =
            EffectFactory()

        val effect =
            factory.create(
                listOf("vol$.5"),
                44100
            )

        val output =
            effect.apply(
                doubleArrayOf(1.0)
            )

        assertEquals(
            0.5,
            output[0]
        )
    }


    @Test
    fun `effect factory creates multiple stacked effects`() {
        val factory =
            EffectFactory()

        val effect =
            factory.create(
                listOf(
                    "vol$.5",
                    "clip$.2"
                ),
                44100
            )

        val output =
            effect.apply(
                doubleArrayOf(1.0)
            )

        assertEquals(
            0.2,
            output[0]
        )
    }


    @Test
    fun `effect factory rejects unknown effect`() {
        val factory =
            EffectFactory()

        assertThrows(
            IllegalArgumentException::class.java
        ) {
            factory.create(
                listOf("echo$5"),
                44100
            )
        }
    }
}