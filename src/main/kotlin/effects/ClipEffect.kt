package effects

class ClipEffect(
    effect: AudioEffect,
    private val threshold: Double
) : EffectDecorator(effect) {
    override fun apply(samples: DoubleArray): DoubleArray {
        val result = super.apply(samples)

        return result.map {
            when {
                it > threshold -> threshold
                it < -threshold -> -threshold
                else -> it
            }
        }.toDoubleArray()
    }
}