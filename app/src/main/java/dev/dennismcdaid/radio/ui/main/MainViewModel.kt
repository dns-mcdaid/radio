package dev.dennismcdaid.radio.ui.main

import androidx.lifecycle.*
import dev.dennismcdaid.radio.data.StationRepository
import dev.dennismcdaid.radio.data.model.Program
import dev.dennismcdaid.radio.ui.Event
import dev.dennismcdaid.radio.ui.StreamAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val stationRepo: StationRepository
) : ViewModel() {

    private val mainContext = Dispatchers.Default + viewModelScope.coroutineContext

    private val playing = MutableStateFlow(false)

    val toggleStream = MutableStateFlow(false)

    val playerState: LiveData<PlayerViewState> = stationRepo.getOnAir()
        .flowOn(Dispatchers.IO)
        .map { it.first().program }
        .combine<Program, Boolean, PlayerViewState>(playing) { program, playing ->
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

    fun onPlayClicked() {
        playing.value = !playing.value
        if (!playing.value) {
            actionChannel.offer(StreamAction.Stop)
            return
        }
        viewModelScope.launch {
            stationRepo.getStation()
                .flowOn(Dispatchers.IO)
                .map { station ->
                    station.streams.maxBy { it.format }?.url
                        ?: throw IllegalStateException("No stream found")
                }
                .map<String, StreamAction> {
                    StreamAction.Start(it)
                }
                .catch {
                    emit(StreamAction.Error)
                }
                .collect { actionChannel.offer(it) }
        }
    }
}