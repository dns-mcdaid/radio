package dev.dennismcdaid.radio.ui.program.episode

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.dennismcdaid.radio.data.StationRepository
import dev.dennismcdaid.radio.ui.base.BaseViewModel
import dev.dennismcdaid.radio.util.DateFormatter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class EpisodeViewModel @Inject constructor(
    private val stationRepository: StationRepository
) : BaseViewModel() {

    private val viewState = MutableStateFlow(EpisodeViewState())

    val viewStateLiveData = viewState.asLiveData(mainContext)

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
                    Timber.d("GOT TRACKS")
                    Timber.d(it.toString())
                    viewState.value = viewState.value.copy(tracks = it, loading = false)
                }
        }
    }
}