package dev.dennismcdaid.radio.data.model

import dev.dennismcdaid.radio.data.model.airnet.AirnetTrack
import org.joda.time.LocalDateTime

data class Episode(
    val title: String,
    val status: EpisodeStatus = EpisodeStatus.ARCHIVED,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val timestamp: String = "",
    val totalDuration: Double = 0.0,
    val elapsedDuration: Double = 0.0,
    val description: String? = null,
    val imageUrl: String? = null,
    val playlistUrl: String? = null,
    val playlist: List<AirnetTrack> = emptyList()
)