package dev.dennismcdaid.radio.ui.program

import androidx.lifecycle.asLiveData
import dev.dennismcdaid.radio.data.StationRepository
import dev.dennismcdaid.radio.data.model.emit.EmitProgram
import dev.dennismcdaid.radio.ui.asLiveEvent
import dev.dennismcdaid.radio.ui.base.BaseViewModel
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class ProgramListViewModel @Inject constructor(
    stationRepository: StationRepository
) : BaseViewModel() {

    private val searchFilter = MutableStateFlow("")

    val showClear = searchFilter.map { it.isNotEmpty() }.asLiveData(mainContext)

    private val navigationChannel = BroadcastChannel<String>(1)

    val navigateToProgram = navigationChannel.asLiveEvent(mainContext)

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
        navigationChannel.offer(program.slug)
    }

    fun observeInput(string: String) {
        searchFilter.value = string
    }

    fun clear() {
        searchFilter.value = ""
    }
}