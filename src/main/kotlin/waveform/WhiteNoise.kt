package waveform

import kotlin.random.Random

class WhiteNoise : WaveformStrategy {

    override fun sample(
        frequency: Double,
        time: Double
    ): Double {

        return Random.nextDouble(-1.0, 1.0)

    }
}