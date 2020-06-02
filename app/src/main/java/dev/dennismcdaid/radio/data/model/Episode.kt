package dev.dennismcdaid.radio.data.model

import com.squareup.moshi.Json
import org.joda.time.LocalDateTime

data class Episode(
    val name: String = "",
    val status: EpisodeStatus,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    @Json(name = "programme")
    val program: Program,
    val timestamp: String = "",
    val totalDuration: Double = 0.0,
    val elapsedDuration: Double = 0.0,
    val streams: List<Stream> = emptyList()
)