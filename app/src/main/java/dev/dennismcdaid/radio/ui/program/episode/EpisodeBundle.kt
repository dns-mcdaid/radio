package dev.dennismcdaid.radio.ui.program.episode

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.joda.time.LocalDateTime

@Parcelize
data class EpisodeBundle(
    val programName: String,
    val presenter: String,
    val airDate: LocalDateTime,
    val description: String,
    val playlistUrl: String
) : Parcelable