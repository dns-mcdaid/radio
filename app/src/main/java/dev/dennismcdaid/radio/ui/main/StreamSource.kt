package dev.dennismcdaid.radio.ui.main

import dev.dennismcdaid.radio.data.model.emit.EmitEpisode

sealed class StreamSource {
    object Live : StreamSource()
    data class Episode(val episode: EmitEpisode): StreamSource()
}