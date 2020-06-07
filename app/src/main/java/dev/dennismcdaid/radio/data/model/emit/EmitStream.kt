package dev.dennismcdaid.radio.data.model.emit

import dev.dennismcdaid.radio.data.model.FormatType

data class EmitStream(
    val description: String = "",
    val format: FormatType = FormatType.UNKNOWN,
    val bitrate: Int = 0,
    val url: String = ""
)