package audio

import model.Channel
import model.Note
import waveform.WaveformFactory
import effects.EffectFactory

class Synthesizer {
    private val waveformFactory = WaveformFactory()
    private val effectFactory = EffectFactory()
    fun synthesizeChannel(
        channel: Channel,
        sampleRate: Int,
        tempo: Int
    ): DoubleArray {
        val waveform = waveformFactory.create(channel.waveform)
        val samples = mutableListOf<Double>()

        for (note in channel.notes) {
            val noteSamples =
                generateNote(
                    note,
                    waveform,
                    sampleRate,
                    tempo
                )

            samples.addAll(noteSamples.toList())
        }
        
        val audioEffect = effectFactory.create(
            channel.effects,
            sampleRate
        )

        return audioEffect.apply(
            samples.toDoubleArray()
        )
    }

    private fun generateNote(
        note: Note,
        waveform: waveform.WaveformStrategy,
        sampleRate: Int,
        tempo: Int
    ): DoubleArray {
        // Convert beats into seconds
        val seconds = note.duration * 60.0 / tempo
        val numberOfSamples = (seconds * sampleRate).toInt()
        val output = DoubleArray(numberOfSamples)

        // Rests
        if(note.pitch == "-") {
            return output
        }

        val frequency = PitchConverter.frequency(note.pitch)

        for(i in output.indices) {
            val time = i.toDouble() / sampleRate
            output[i] = waveform.sample(frequency, time)
        }
        return output
    }
}