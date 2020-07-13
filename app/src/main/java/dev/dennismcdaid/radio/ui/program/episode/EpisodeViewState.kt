package dev.dennismcdaid.radio.ui.program.episode

import dev.dennismcdaid.radio.data.model.airnet.AirnetTrack

data class EpisodeViewState(
    val loading: Boolean = false,
    val downloadState: DownloadState = DownloadState.CannotDownload,
    val playing: Boolean = false,
    val title: String = "",
    val description: String = "",
    val tracks: List<AirnetTrack> = emptyList()
)

sealed class DownloadState {
    object CanDownload : DownloadState()
    object Downloaded : DownloadState()
    object CannotDownload : DownloadState()
    data class Downloading(val percentage: Float): DownloadState()
}