package dev.dennismcdaid.radio.ui.program

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.dennismcdaid.radio.data.StationRepository
import dev.dennismcdaid.radio.data.model.Program
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class ProgramListViewModel @Inject constructor(
    private val stationRepository: StationRepository
) : ViewModel() {

    private val mainContext = Dispatchers.Default + viewModelScope.coroutineContext

    val shows = stationRepository.getShows()
        .map { programs ->
            val (noImage, image) = programs.partition { it.imageUrl.isNullOrEmpty() }
            return@map image.sortedBy { it.name } + noImage
        }
        .asLiveData(mainContext)

    fun onProgramClicked(program: Program) {
        Timber.d("Selected: $program")
    }
}