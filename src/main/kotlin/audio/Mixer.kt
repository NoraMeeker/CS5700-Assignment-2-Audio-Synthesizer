package audio

import kotlin.math.abs

class Mixer {

    fun mix(
        channels: List<DoubleArray>
    ): DoubleArray {
        if(channels.isEmpty()) {
            return DoubleArray(0)
        }

        val length = channels.maxOf {
            it.size
        }

        val result = DoubleArray(length)

        for(channel in channels) {
            for(i in channel.indices) {
                result[i] += channel[i]
            }
        }

        val max =
            result.maxOf {
                abs(it)
            }

        if(max > 1.0) {
            for(i in result.indices) {
                result[i] /= max
            }
        }

        return result
    }
}