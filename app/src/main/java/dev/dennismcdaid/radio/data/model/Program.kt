package dev.dennismcdaid.radio.data.model

import dev.dennismcdaid.radio.data.model.emit.EmitEpisode

data class Program(
    val slug: String,
    val programName: String,
    val presenter: String,
    val shortDesc: String,
    val longDesc: String,
    val profileImageUrl: String = "",
    val bannerImageUrl: String = "",
    val facebook: String = "",
    val twitter: String = "",
    val episodes: List<EmitEpisode> = emptyList()
)