package dev.dennismcdaid.radio.data.model.airnet

import org.joda.time.LocalTime

data class AirnetTrack(
    val type: String,
    val id: Long,
    val artist: String,
    val title: String,
    val track: String,
    val release: String,
    val time: LocalTime,
    val notes: String?,
    val twitterHandle: String?,
    val contentDescriptors: Map<String, Boolean>,
    val wikipedia: String?,
    val image: String?,
    val video: String?,
    val url: String?,
    val approximateTime: String?
)