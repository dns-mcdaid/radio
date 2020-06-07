package dev.dennismcdaid.radio.ui.program

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.dennismcdaid.radio.data.StationRepository
import dev.dennismcdaid.radio.data.model.emit.EmitProgram
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class ProgramListViewModel @Inject constructor(
    private val stationRepository: StationRepository
) : ViewModel() {

    private val mainContext = Dispatchers.Default + viewModelScope.coroutineContext

    private val searchFilter = MutableStateFlow("")

    val showClear = searchFilter.map { it.isNotEmpty() }.asLiveData(mainContext)

    val shows = stationRepository.getShows()
        .map { programs ->
            val (noImage, image) = programs.partition { it.imageUrl.isNullOrEmpty() }
            return@map image.sortedBy { it.name } + noImage
        }
        .combine(searchFilter) { programs, filter ->
            return@combine if (filter.isEmpty()) programs
            else programs.applyFilter(filter)
        }
        .asLiveData(mainContext)

    private fun List<EmitProgram>.applyFilter(filter: String): List<EmitProgram> {
        val lowerCase = filter.toLowerCase(Locale.ENGLISH)
        return filter {
            it.name.toLowerCase(Locale.ENGLISH).contains(lowerCase)
                    || it.presenter.toLowerCase(Locale.ENGLISH).contains(lowerCase)
                    || it.description?.toLowerCase(Locale.ENGLISH)?.contains(lowerCase) ?: false
        }
    }

    fun onProgramClicked(program: EmitProgram) {
        Timber.d("Selected: $program")
        Timber.d("Querying details...")
        viewModelScope.launch {
            stationRepository.getProgram(program.slug)
                .catch { e ->
                    Timber.w(e, "Error collecting details")
                }
                .collect {
                    Timber.d("Found results: $it")
                }
        }
    }

    fun observeInput(string: String) {
        searchFilter.value = string
    }

    fun clear() {
        searchFilter.value = ""
    }
}