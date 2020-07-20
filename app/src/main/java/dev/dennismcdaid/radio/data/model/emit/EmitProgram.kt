package dev.dennismcdaid.radio.data.model.emit

import dev.dennismcdaid.radio.util.asHttps

data class EmitProgram(
    val name: String = "",
    val slug: String = "",
    val presenter: String = "",
    val description: String? = null,
    val imageUrl: String? = null,
    val twitterHandle: String? = null,
    val facebookId: String? = null,
    val lastEpisode: EmitEpisode? = null,
    val nextEpisode: EmitEpisode? = null,
    val currentEpisode: EmitEpisode? = null
) {
    val avatarUrl : String? = imageUrl?.asHttps()
}