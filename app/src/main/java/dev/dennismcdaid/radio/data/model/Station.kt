package dev.dennismcdaid.radio.data.model

data class Station(
    val schedule: String = "",
    val name: String = "",
    val slug: String = "",
    val callsign: String = "",
    val streams: List<Stream> = emptyList(),
    val timeZone: String = "",
    val frequency: String = "",
    val recordingEnabled: Boolean = false,
    val recordingProfiles: List<String> = emptyList(),
    val description: String = ""
)