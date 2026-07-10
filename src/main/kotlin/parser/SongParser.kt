package parser

import model.Channel
import model.Note
import model.Song
import model.SongHeader
import java.io.File

class SongParser {

    fun parse(filename: String): Song {

        val lines = try {
            File(filename).readLines()
        } catch (e: Exception) {
            throw IllegalArgumentException(
                "Could not read file: $filename"
            )
        }

        if (lines.isEmpty()) {
            throw IllegalArgumentException(
                "Song file is empty"
            )
        }


        // Parse header
        // --------------------------------------------------------------------------------------------------------------------------------

        val headerParts = lines[0].trim().split(" ")

        if (headerParts.size != 3) {
            throw IllegalArgumentException(
                "Invalid song header. Expected: sampleRate beatsPerMeasure tempo"
            )
        }

        val header = SongHeader(
            sampleRate = headerParts[0].toInt(),
            beatsPerMeasure = headerParts[1].toInt(),
            tempo = headerParts[2].toInt()
        )


        // Parse channels
        // --------------------------------------------------------------------------------------------------------------------------------

        val channels = mutableListOf<Channel>()

        for (i in 1 until lines.size) {

            val line = lines[i].trim()

            // Skip empty lines
            if (line.isEmpty()) {
                continue
            }

            val sections = line.split("|")


            // Channel settings
            // --------------------------------------------------------------------------------------------------------------------------------

            val settings = sections[0]
                .trim()
                .split(" ")

            if (settings.isEmpty()) {
                throw IllegalArgumentException(
                    "Channel is missing waveform"
                )
            }

            val waveform = settings[0]

            val effects = settings.drop(1)



            // Notes
            // --------------------------------------------------------------------------------------------------------------------------------

            val notes = mutableListOf<Note>()

            for (j in 1 until sections.size) {

                val measure = sections[j]
                    .trim()

                if (measure.isEmpty()) {
                    continue
                }

                val tokens = measure.split(" ")

                if (tokens.size % 2 != 0) {
                    throw IllegalArgumentException(
                        "Invalid note format in channel $i"
                    )
                }

                var k = 0

                while (k < tokens.size) {

                    val pitch = tokens[k]

                    val duration = try {
                        tokens[k + 1].toDouble()
                    } catch (e: Exception) {
                        throw IllegalArgumentException(
                            "Invalid duration: ${tokens[k + 1]}"
                        )
                    }

                    notes.add(
                        Note(
                            pitch = pitch,
                            duration = duration
                        )
                    )
                    k += 2
                }
            }

            channels.add(
                Channel(
                    waveform = waveform,
                    effects = effects,
                    notes = notes
                )
            )
        }

        return Song(
            header = header,
            channels = channels
        )
    }
}