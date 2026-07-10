package effects

import kotlin.math.tanh

class TanhEffect(
    effect: AudioEffect,
    private val drive: Double
) : EffectDecorator(effect) {

    override fun apply(samples: DoubleArray): DoubleArray {
        val result = super.apply(samples)

        return result.map {
            tanh(it * drive)
        }.toDoubleArray()
    }
}