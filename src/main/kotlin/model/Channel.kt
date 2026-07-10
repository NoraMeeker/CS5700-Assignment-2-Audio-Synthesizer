package model

data class Channel(
    val waveform: String,
    val effects: List<String>,
    val notes: List<Note>
)