package dev.dennismcdaid.radio.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.dennismcdaid.radio.data.StationRepository
import dev.dennismcdaid.radio.data.model.emit.EmitProgram
import dev.dennismcdaid.radio.ui.asLiveEvent
import dev.dennismcdaid.radio.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ScheduleViewModel @Inject constructor(
    stationRepo: StationRepository
) : BaseViewModel() {

    private val navigationChannel = BroadcastChannel<String>(1)

    val navigateToProgram = navigationChannel.asLiveEvent(mainContext)

    val schedule = stationRepo.getSchedule()
        .map { episodes ->
            val headers = episodes.map { it.startTime.toLocalDate() }
                .distinct()
                .map { ScheduleRow.Header(it) }
            val items = episodes.map { ScheduleRow.Item(it) }

            return@map (headers + items).sorted()
        }
        .asLiveData(mainContext)


    fun onProgramClicked(program: EmitProgram) {
        navigationChannel.offer(program.slug)
    }
}