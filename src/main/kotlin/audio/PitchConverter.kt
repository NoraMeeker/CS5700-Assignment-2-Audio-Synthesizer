package audio

import kotlin.math.pow

object PitchConverter {
    private val notes = mapOf(
        "C" to 0,
        "C#" to 1,
        "Db" to 1,
        "D" to 2,
        "D#" to 3,
        "Eb" to 3,
        "E" to 4,
        "F" to 5,
        "F#" to 6,
        "Gb" to 6,
        "G" to 7,
        "G#" to 8,
        "Ab" to 8,
        "A" to 9,
        "A#" to 10,
        "Bb" to 10,
        "B" to 11
    )


    fun frequency(note: String): Double {
        if(note == "-") {
            return 0.0
        }

        val name =
            if(note.length == 3)
                note.substring(0,2)
            else
                note.substring(0,1)

        val octave = note.last().digitToInt()

        val midi = (octave + 1) * 12 + notes[name]!!

        return 440.0 * 2.0.pow(
            (midi - 69) / 12.0
        )
    }
}