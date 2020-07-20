package dev.dennismcdaid.radio.ui.program.episode

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.dennismcdaid.radio.data.StationRepository
import dev.dennismcdaid.radio.data.model.emit.EmitEpisode
import dev.dennismcdaid.radio.service.EpisodeDownloader
import dev.dennismcdaid.radio.ui.asLiveEvent
import dev.dennismcdaid.radio.ui.base.BaseViewModel
import dev.dennismcdaid.radio.util.DateFormatter
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class EpisodeViewModel @Inject constructor(
    private val stationRepository: StationRepository,
    private val episodeDownloader: EpisodeDownloader
) : BaseViewModel() {

    private val viewState = MutableStateFlow(EpisodeViewState())

    val viewStateLiveData = viewState.asLiveData(mainContext)

    private val playEpisodeChannel = BroadcastChannel<EmitEpisode>(1)

    val playEpisodeEvent = playEpisodeChannel.asLiveEvent(mainContext)

    fun loadEpisode(bundle: EpisodeBundle) {
        viewState.value = EpisodeViewState(
            title = DateFormatter.episodeDate(bundle.airDate),
            description = bundle.description,
            loading = true
        )

        viewModelScope.launch {
            stationRepository.getTracks(bundle.playlistUrl)
                .catch { e ->
                    Timber.e(e, "Error occurred!")
                    emit(emptyList())
                }
                .collect {
                    viewState.value = viewState.value.copy(tracks = it, loading = false)
                }

            episodeDownloader.episodeIsDownloaded(bundle.slug, bundle.airDate)
                .collect {
                    viewState.value = viewState.value.copy(downloaded = it)
                }
        }
    }

    fun playEpisode(bundle: EpisodeBundle) {
        viewModelScope.launch {
            stationRepository.getEpisode(bundle.slug, DateFormatter.episodeDownloadPath(bundle.airDate))
                .catch { e ->
                    Timber.e(e, "Error fetching episode")
                }
                .collect {
                    playEpisodeChannel.offer(it)
                }
        }
    }

    fun onDownloadClicked(bundle: EpisodeBundle) {
        viewModelScope.launch {
            viewState.value = viewState.value.copy(loading = true)
            if (viewState.value.downloaded) {
                episodeDownloader.deleteEpisode(bundle.slug, bundle.airDate)
                viewState.value = viewState.value.copy(loading = false, downloaded = false)
            } else {
                episodeDownloader.downloadEpisode(bundle.slug, bundle.airDate, bundle.description, bundle.playlistUrl)
                    .collect {
                        viewState.value = viewState.value.copy(loading = false)
                    }
            }
        }
    }
}