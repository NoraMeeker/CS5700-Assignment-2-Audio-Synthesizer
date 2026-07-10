package effects

class RawAudio : AudioEffect {
    override fun apply(samples: DoubleArray): DoubleArray {
        return samples
    }
}