package dev.dennismcdaid.radio.data.model

import com.squareup.moshi.FromJson
import java.util.*

enum class EpisodeStatus {
    SCHEDULED,
    ONAIR,
    ARCHIVED;

    companion object Adapter {
        @FromJson
        fun fromJson(string: String): EpisodeStatus {
            return values().firstOrNull {
                it.name.toLowerCase(Locale.ENGLISH) == string.toLowerCase(Locale.ENGLISH)
            } ?: ARCHIVED
        }
    }
}