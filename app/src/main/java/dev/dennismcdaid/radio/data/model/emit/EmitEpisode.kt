package dev.dennismcdaid.radio.data.model.emit

import com.squareup.moshi.Json
import dev.dennismcdaid.radio.data.model.EpisodeStatus
import dev.dennismcdaid.radio.util.asHttps
import org.joda.time.LocalDateTime

data class EmitEpisode(
    val name: String = "",
    val status: EpisodeStatus,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    @Json(name = "programme")
    val program: EmitProgram,
    val timestamp: String = "",
    val totalDuration: Double = 0.0,
    val elapsedDuration: Double = 0.0,
    val streams: List<EpisodeStream> = emptyList()
) {
    val audioUrl: String? = streams.maxBy { it.bitrate }?.url?.asHttps()
}