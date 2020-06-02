package dev.dennismcdaid.radio.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.dennismcdaid.radio.data.StationRepository
import dev.dennismcdaid.radio.data.model.Episode
import dev.dennismcdaid.radio.data.model.Program
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val stationRepo: StationRepository
) : ViewModel() {

    private val mainContext = Dispatchers.Default + viewModelScope.coroutineContext

    private val playing = MutableStateFlow(false)

    val toggleStream = MutableStateFlow(false)

    val stream = stationRepo.getStation()
        .map { station ->
            station.streams.maxBy { it.format }?.url ?: ""
        }
        .combine(toggleStream.filter { it }) { s, b ->
            Pair(s, b)
        }
        .asLiveData(mainContext)

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
            emit(PlayerViewState.Error(e.localizedMessage ?: "An error occurred"))
        }
        .asLiveData(mainContext)

    fun onPlayClicked() {
        toggleStream.value = !toggleStream.value
    }
}