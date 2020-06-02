package dev.dennismcdaid.radio.data.model

import com.squareup.moshi.FromJson
import java.util.*

enum class FormatType {
    AAC,
    AACP,
    MP3,
    UNKNOWN;

    companion object Adapter {

        @FromJson
        fun fromJson(str: String): FormatType {
            return values().firstOrNull {
                it.name.toLowerCase(Locale.ENGLISH) == str.toLowerCase(Locale.ENGLISH)
            } ?: UNKNOWN
        }
    }
}