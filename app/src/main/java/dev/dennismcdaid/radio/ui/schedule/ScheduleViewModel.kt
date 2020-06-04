package dev.dennismcdaid.radio.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.dennismcdaid.radio.data.StationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ScheduleViewModel @Inject constructor(
    private val stationRepo: StationRepository
) : ViewModel() {

    private val mainContext = Dispatchers.Default + viewModelScope.coroutineContext

    val schedule = stationRepo.getSchedule()
        .map { episodes ->
            val headers = episodes.map { it.startTime.toLocalDate() }
                .distinct()
                .map { ScheduleRow.Header(it) }
            val items = episodes.map { ScheduleRow.Item(it) }

            return@map (headers + items).sorted()
        }
        .asLiveData(mainContext)
}