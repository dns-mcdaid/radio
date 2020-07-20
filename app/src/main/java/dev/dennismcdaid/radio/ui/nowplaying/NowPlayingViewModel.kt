package dev.dennismcdaid.radio.ui.nowplaying

import dev.dennismcdaid.radio.data.StationRepository
import dev.dennismcdaid.radio.ui.base.BaseViewModel
import javax.inject.Inject

class NowPlayingViewModel @Inject constructor(private val stationRepository: StationRepository) : BaseViewModel() {

}