package effects

interface AudioEffect {
    fun apply(samples: DoubleArray): DoubleArray
}