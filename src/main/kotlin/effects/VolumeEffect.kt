package effects

class VolumeEffect(
    effect: AudioEffect,
    private val level: Double
) : EffectDecorator(effect) {

    override fun apply(samples: DoubleArray): DoubleArray {
        val result = super.apply(samples)

        return result.map {
            it * level
        }.toDoubleArray()
    }
}