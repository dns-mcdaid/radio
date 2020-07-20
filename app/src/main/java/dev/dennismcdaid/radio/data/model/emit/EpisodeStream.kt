package dev.dennismcdaid.radio.data.model.emit

import com.squareup.moshi.Json
import dev.dennismcdaid.radio.data.model.FormatType

data class EpisodeStream(
    val name: String,
    val format: FormatType = FormatType.UNKNOWN,
    val bitrate: Int,
    @Json(name = "href")
    val url: String
)