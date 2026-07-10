package waveform

interface WaveformStrategy {
    fun sample(
        frequency: Double,
        time: Double
    ): Double
}