package dev.dennismcdaid.radio.data.model.airnet

import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat

data class AirnetEpisode(
    val url: String?,
    val start: String,
    val end: String,
    val duration: Double,
    val multipleEpsOnDay: Boolean,
    val title: String?,
    val description: String?,
    val imageUrl: String?,
    val episodeRestUrl: String
) {

    val startTime: LocalDateTime
        get() = DateTimeFormat.forPattern(AIRNET_FORMAT).parseLocalDateTime(start)

    val endTime: LocalDateTime
        get() = DateTimeFormat.forPattern(AIRNET_FORMAT).parseLocalDateTime(end)

    val playlistUrl: String
        get() = "$episodeRestUrl/playlists"

    val timestamp: String
        get() = Regex("[^0-9.]").replace(start, "").take(12)

    companion object {
        private val AIRNET_FORMAT = "yyy-MM-dd HH:mm:ss"
    }
}