package waveform

class SawWave : WaveformStrategy {
    override fun sample(
        frequency: Double,
        time: Double
    ): Double {
        val period = 1.0 / frequency
        return 2 * ((time / period) % 1.0) - 1
    }
}