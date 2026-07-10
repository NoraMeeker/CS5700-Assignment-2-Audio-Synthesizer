package effects

class ADSEffect(
    effect: AudioEffect,
    private val attackEnd: Double,
    private val decayEnd: Double,
    private val sustain: Double,
    private val sampleRate: Int
) : EffectDecorator(effect) {

    override fun apply(samples: DoubleArray): DoubleArray {
        val result = super.apply(samples)

        return result.mapIndexed { index, sample ->
            val time = index.toDouble() / sampleRate

            val multiplier = when {
                attackEnd > 0 && time < attackEnd -> time / attackEnd
                time < decayEnd -> 1.0 - ((time - attackEnd) / (decayEnd - attackEnd)) * (1 - sustain)
                else -> sustain
            }

            sample * multiplier.coerceIn(0.0, 1.0)

        }.toDoubleArray()
    }
}