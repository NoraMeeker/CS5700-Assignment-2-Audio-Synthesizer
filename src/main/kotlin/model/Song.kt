package model

data class Song(
    val header: SongHeader,
    val channels: List<Channel>
)