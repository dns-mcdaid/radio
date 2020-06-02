package dev.dennismcdaid.radio.data.model

data class Stream(
    val description: String = "",
    val format: FormatType = FormatType.UNKNOWN,
    val bitrate: Int = 0,
    val url: String = ""
)