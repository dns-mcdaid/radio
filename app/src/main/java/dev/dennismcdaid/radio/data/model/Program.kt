package dev.dennismcdaid.radio.data.model

data class Program(
    val name: String = "",
    val slug: String = "",
    val presenter: String = "",
    val description: String? = null,
    val imageUrl: String? = null,
    val twitterHandle: String? = null,
    val facebookId: String? = null,
    val lastEpisode: Episode? = null,
    val nextEpisode: Episode? = null,
    val currentEpisode: Episode? = null
)