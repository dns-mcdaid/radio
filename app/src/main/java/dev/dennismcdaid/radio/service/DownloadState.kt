package dev.dennismcdaid.radio.service

sealed class DownloadState {
    object Downloadable : DownloadState()
    object Downloaded : DownloadState()
    object CannotDownload : DownloadState()
    data class Downloading(val percentage: Float): DownloadState()
}