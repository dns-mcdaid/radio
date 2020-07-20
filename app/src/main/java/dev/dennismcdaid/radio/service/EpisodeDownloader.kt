package dev.dennismcdaid.radio.service

import dev.dennismcdaid.radio.data.model.emit.EmitEpisode
import kotlinx.coroutines.flow.Flow
import org.joda.time.LocalDateTime

interface EpisodeDownloader {
    fun episodeIsDownloaded(
        slug: String,
        airDateTime: LocalDateTime
    ): Flow<Boolean>

    fun downloadEpisode(
        slug: String,
        airDateTime: LocalDateTime,
        description: String,
        playlistUrl: String
    ): Flow<EmitEpisode>

    suspend fun deleteEpisode(slug: String, airDateTime: LocalDateTime)
}