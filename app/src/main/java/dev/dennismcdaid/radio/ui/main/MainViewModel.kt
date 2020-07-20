package dev.dennismcdaid.radio.ui.main

import androidx.lifecycle.*
import dev.dennismcdaid.radio.data.StationRepository
import dev.dennismcdaid.radio.data.model.emit.EmitEpisode
import dev.dennismcdaid.radio.data.model.emit.EmitProgram
import dev.dennismcdaid.radio.ui.Event
import dev.dennismcdaid.radio.ui.StreamAction
import dev.dennismcdaid.radio.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val stationRepo: StationRepository
) : BaseViewModel() {

    private val playing = MutableStateFlow(false)
    private val streamSource = MutableStateFlow<StreamSource>(StreamSource.Live)

    private val program = streamSource.flatMapLatest { source ->
        when (source) {
            StreamSource.Live -> stationRepo.getOnAir()
                .flowOn(Dispatchers.IO)
                .map { it.first().program }
            is StreamSource.Episode -> flowOf(source.episode.program)
        }
    }

    val playerState: LiveData<PlayerViewState> = program
        .combine<EmitProgram, Boolean, PlayerViewState>(playing) { program, playing ->
            PlayerViewState.Active(
                program.name,
                program.description ?: "",
                program.imageUrl ?: "",
                playing
            )
        }
        .onStart { emit(PlayerViewState.Loading) }
        .catch { e ->
            emit(
                PlayerViewState.Error(
                    e.localizedMessage ?: "An error occurred"
                )
            )
        }
        .asLiveData(mainContext)

    private val actionChannel = ConflatedBroadcastChannel<StreamAction>()
    val streamAction = liveData {
        actionChannel.asFlow().collect {
            emit(Event(it))
        }
    }

    private fun getPlaybackUrl(streamSource: StreamSource): Flow<String> {
        return when (streamSource) {
            StreamSource.Live -> stationRepo.getStation()
                .flowOn(Dispatchers.IO)
                .map { station ->
                    station.streams.maxBy { it.format }?.url
                        ?: throw IllegalStateException("No stream found")
                }
            is StreamSource.Episode -> flowOf(streamSource.episode.audioUrl!!)
        }
    }

    fun onPlayClicked() {
        playing.value = !playing.value
        if (!playing.value) {
            actionChannel.offer(StreamAction.Stop)
            return
        }
        viewModelScope.launch {
            getPlaybackUrl(streamSource.value)
                .map<String, StreamAction> {
                    StreamAction.Start(it)
                }
                .catch {
                    emit(StreamAction.Error)
                }
                .collect { actionChannel.offer(it) }
        }
    }

    fun playEpisode(episode: EmitEpisode) {
        streamSource.value = StreamSource.Episode(episode)
        // TODO: Not this
        playing.value = false
        onPlayClicked()
    }
}