package dev.dennismcdaid.radio.service

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.asFlow
import androidx.work.*
import dev.dennismcdaid.radio.data.StationRepository
import dev.dennismcdaid.radio.data.model.EpisodeDownload
import dev.dennismcdaid.radio.data.model.emit.EmitEpisode
import dev.dennismcdaid.radio.data.source.local.EpisodeDownloadDao
import dev.dennismcdaid.radio.util.DateFormatter
import kotlinx.coroutines.flow.*
import org.joda.time.LocalDateTime
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConcreteEpisodeDownloader @Inject constructor(
    private val context: Context,
    private val stationRepository: StationRepository,
    private val downloadManager: DownloadManager,
    private val episodesDao: EpisodeDownloadDao
) : EpisodeDownloader {

    override fun downloadEpisode(
        slug: String,
        airDateTime: LocalDateTime,
        description: String,
        playlistUrl: String
    ): Flow<EmitEpisode> {
        val pathFormatted = DateFormatter.episodeDownloadPath(airDateTime)

        return stationRepository.getEpisode(slug, pathFormatted)
            .transform {
                startDownloading(it, fileName(slug, airDateTime))
                episodesDao.insertEpisode(EpisodeDownload(fileName(slug, airDateTime), slug, airDateTime, description, playlistUrl, it))
                emit(it)
            }
    }

    override fun episodeIsDownloaded(slug: String, airDateTime: LocalDateTime): Flow<Boolean> {
        val fileName = fileName(slug, airDateTime)
        val file = File(context.externalMediaDirs.first(), fileName)
        return flowOf(file.exists())
    }

    override suspend fun deleteEpisode(slug: String, airDateTime: LocalDateTime) {
        val fileName = fileName(slug, airDateTime)
        val file = File(context.externalMediaDirs.first(), fileName)
        if (file.exists()) file.delete()
    }

    private fun fileName(slug: String, airDateTime: LocalDateTime): String {
        return "${slug}-${DateFormatter.episodeDownloadPath(airDateTime)}.m4a"
    }

    private fun startDownloading(episode: EmitEpisode, fileName: String) {
        val audioUrl = episode.audioUrl ?: return
        val file = File(context.externalMediaDirs.first(), fileName)

        val request = DownloadManager.Request(audioUrl.toUri())
            .setTitle(episode.name)
            .setDescription("Downloading")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setDestinationUri(Uri.fromFile(file))
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(false)

        downloadManager.enqueue(request)
    }
}