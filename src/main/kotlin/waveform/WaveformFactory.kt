package waveform

class WaveformFactory {
    fun create(name: String): WaveformStrategy {
        return when(name.lowercase()) {
            "sin" -> SineWave()
            "square" -> SquareWave()
            "saw" -> SawWave()
            "whitenoise" -> WhiteNoise()
            else -> throw IllegalArgumentException("Unknown waveform: $name")
        }
    }
}