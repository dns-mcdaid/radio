package dev.dennismcdaid.radio.ui.settings

import dev.dennismcdaid.radio.service.EpisodeDownloader
import dev.dennismcdaid.radio.ui.base.BaseViewModel
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val episodeDownloader: EpisodeDownloader
) : BaseViewModel() {

    fun clearStorage() {

    }
}