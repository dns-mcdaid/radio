package dev.dennismcdaid.radio.data.model.emit

data class EmitStation(
    val schedule: String = "",
    val name: String = "",
    val slug: String = "",
    val callsign: String = "",
    val streams: List<EmitStream> = emptyList(),
    val timeZone: String = "",
    val frequency: String = "",
    val recordingEnabled: Boolean = false,
    val recordingProfiles: List<String> = emptyList(),
    val description: String = ""
)