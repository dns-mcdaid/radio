package dev.dennismcdaid.radio.ui.nowplaying

import dev.dennismcdaid.radio.data.StationRepository
import dev.dennismcdaid.radio.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NowPlayingViewModel @Inject constructor(
    private val stationRepo: StationRepository
) : BaseViewModel() {
}