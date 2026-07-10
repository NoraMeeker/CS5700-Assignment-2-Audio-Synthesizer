package effects

abstract class EffectDecorator(
    protected val effect: AudioEffect
) : AudioEffect {

    override fun apply(samples: DoubleArray): DoubleArray {
        return effect.apply(samples)
    }
}