package waveform
import kotlin.math.PI
import kotlin.math.sin

class SineWave : WaveformStrategy {

    override fun sample(
        frequency: Double,
        time: Double
    ): Double {

        return sin(2 * PI * frequency * time)

    }
}