package waveform
import kotlin.math.PI
import kotlin.math.sin

class SquareWave : WaveformStrategy {

    override fun sample(
        frequency: Double,
        time: Double
    ): Double {

        return if (sin(2 * PI * frequency * time) >= 0)
            1.0
        else
            -1.0
    }
}