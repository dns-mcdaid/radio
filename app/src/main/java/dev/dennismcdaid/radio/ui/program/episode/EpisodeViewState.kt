package dev.dennismcdaid.radio.ui.program.episode

import dev.dennismcdaid.radio.data.model.airnet.AirnetTrack

data class EpisodeViewState(
    val loading: Boolean = false,
    val downloaded: Boolean = false,
    val playing: Boolean = false,
    val title: String = "",
    val description: String = "",
    val tracks: List<AirnetTrack> = emptyList()
)