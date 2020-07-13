package dev.dennismcdaid.radio.ui.program.detail

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.dennismcdaid.radio.data.StationRepository
import dev.dennismcdaid.radio.data.model.Episode
import dev.dennismcdaid.radio.ui.asLiveEvent
import dev.dennismcdaid.radio.ui.base.BaseViewModel
import dev.dennismcdaid.radio.ui.program.episode.EpisodeBundle
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProgramDetailViewModel @Inject constructor(
    private val stationRepository: StationRepository
) : BaseViewModel() {

    private val viewState = MutableStateFlow<ProgramDetailViewState>(ProgramDetailViewState.Loading)

    val viewStateLiveData = viewState.asLiveData(mainContext)

    private val episodeEmitter = BroadcastChannel<EpisodeBundle>(1)

    val navigateToEpisode = episodeEmitter.asLiveEvent(mainContext)

    fun setSlug(slug: String) {
        viewModelScope.launch {
            stationRepository.getProgram(slug)
                .catch { e ->
                    viewState.value = ProgramDetailViewState.Error(e.localizedMessage)
                }
                .collect {
                    viewState.value = ProgramDetailViewState.Loaded(it)
                }
        }
    }

    fun onEpisodeClicked(episode: Episode) {
        val program = (viewState.value as? ProgramDetailViewState.Loaded)?.program ?: run {
            snackbarEmitter.offer("Error loading episode")
            return
        }

        val playlistUrl = episode.playlistUrl ?: run {
            snackbarEmitter.offer("Episode wasn't recorded")
            return
        }

        val bundle = EpisodeBundle(
            program.programName,
            program.presenter,
            episode.startTime,
            episode.description ?: episode.title ?: program.longDesc,
            playlistUrl
        )
        episodeEmitter.offer(bundle)
    }
}