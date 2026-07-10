package effects

class EffectFactory {
    fun create(
        effectStrings: List<String>,
        sampleRate: Int
    ): AudioEffect {
        var effect: AudioEffect = RawAudio()

        for (effectString in effectStrings) {
            val parts = effectString.split("$")

            when(parts[0].lowercase()) {

                "vol" -> {
                    effect = VolumeEffect(
                        effect,
                        parts[1].toDouble()
                    )
                }

                "ads" -> {
                    effect = ADSEffect(
                        effect,
                        parts[1].toDouble(),
                        parts[2].toDouble(),
                        parts[3].toDouble(),
                        sampleRate
                    )
                }

                "tanh" -> { effect = TanhEffect(effect, parts[1].toDouble()) }

                "clip" -> { effect = ClipEffect(effect, parts[1].toDouble()) }

                else -> {
                    throw IllegalArgumentException("Unknown effect: ${parts[0]}")
                }
            }
        }
        return effect
    }
}