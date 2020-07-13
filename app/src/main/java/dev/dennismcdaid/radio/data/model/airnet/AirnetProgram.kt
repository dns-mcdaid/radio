package dev.dennismcdaid.radio.data.model.airnet

data class AirnetProgram(
    val bannerImageUrl: String = "",
    val broadcasters: String = "",
    val description: String? = "",
    val episodesRestUrl: String = "",
    val facebookPage: String? = null,
    val gridDescription: String = "",
    val name: String = "",
    val profileImageUrl: String = "",
    val slug: String = "",
    val twitterHandle: String? = null,
    val url: String? = null
)