import audio.AudioPlayer
import audio.Mixer
import audio.Synthesizer
import parser.SongParser

fun main() {
    try {
        val filename = "src/main/resources/sarias_song.txt"
        val song = SongParser().parse(filename)
        val synthesizer = Synthesizer()
        val mixer = Mixer()
        val channelSamples = mutableListOf<DoubleArray>()

        for(channel in song.channels) {
            val samples =
                synthesizer.synthesizeChannel(
                    channel,
                    song.header.sampleRate,
                    song.header.tempo
                )

            channelSamples.add(samples)
        }

        val finalSamples = mixer.mix(channelSamples)
        val player = AudioPlayer()

        player.play(finalSamples, song.header.sampleRate)
        println("Final samples: ${finalSamples.size}")
        println(finalSamples.take(10))

    } catch(e: Exception) { println("Error: ${e.message}") }
}