package dev.dennismcdaid.radio.data.model

import dev.dennismcdaid.radio.data.model.airnet.AirnetTrack
import org.joda.time.LocalDateTime

data class Episode(
    val name: String,
    val status: EpisodeStatus,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val timestamp: String = "",
    val totalDuration: Double = 0.0,
    val elapsedDuration: Double = 0.0,
    val imageUrl: String?,
    val episodeRestUrl: String?,
    val playlist: List<AirnetTrack> = emptyList()
)