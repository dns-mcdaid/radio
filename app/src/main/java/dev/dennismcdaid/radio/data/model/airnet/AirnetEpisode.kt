package dev.dennismcdaid.radio.data.model.airnet

import org.joda.time.LocalDateTime

data class AirnetEpisode(
    val url: String?,
    val start: LocalDateTime,
    val end: LocalDateTime,
    val duration: Double,
    val multipleEpsOnDay: Boolean,
    val title: String?,
    val description: String?,
    val imageUrl: String?,
    val episodeRestUrl: String
)