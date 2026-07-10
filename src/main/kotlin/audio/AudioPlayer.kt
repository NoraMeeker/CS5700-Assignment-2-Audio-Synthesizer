package audio

import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem

class AudioPlayer {

    fun play(
        samples: DoubleArray,
        sampleRate: Int
    ) {
        val format =
            AudioFormat(
                sampleRate.toFloat(),
                16,
                1,
                true,
                false
            )

        val line =
            AudioSystem.getSourceDataLine(format)

        line.open(format)
        line.start()

        val buffer = ByteArray(samples.size * 2)

        for(i in samples.indices) {
            val value =
                (samples[i] * Short.MAX_VALUE)
                    .toInt()

            buffer[i * 2] =
                value.toByte()

            buffer[i * 2 + 1] =
                (value shr 8).toByte()
        }

        line.write(
            buffer,
            0,
            buffer.size
        )

        line.drain()
        line.close()
    }
}