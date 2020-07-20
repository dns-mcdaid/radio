package dev.dennismcdaid.radio.ui.saved

import androidx.lifecycle.asLiveData
import dev.dennismcdaid.radio.data.model.EpisodeDownload
import dev.dennismcdaid.radio.data.source.local.EpisodeDownloadDao
import dev.dennismcdaid.radio.ui.asLiveEvent
import dev.dennismcdaid.radio.ui.base.BaseViewModel
import dev.dennismcdaid.radio.ui.program.episode.EpisodeBundle
import kotlinx.coroutines.channels.BroadcastChannel
import javax.inject.Inject

class SavedViewModel @Inject constructor(
    private val episodeDao: EpisodeDownloadDao
) : BaseViewModel() {

    private val episodeEmitter = BroadcastChannel<EpisodeBundle>(1)

    val navigateToEpisode = episodeEmitter.asLiveEvent(mainContext)

    val downloadedEpisodes = episodeDao.getDownloadedEpisodes()
        .asLiveData(mainContext)

    fun onEpisodeClicked(episode: EpisodeDownload) {
        val bundle = EpisodeBundle(
            episode.programSlug,
            episode.programName,
            episode.presenter,
            episode.recordedAt,
            episode.description,
            episode.playlistUrl
        )
        episodeEmitter.offer(bundle)
    }
}