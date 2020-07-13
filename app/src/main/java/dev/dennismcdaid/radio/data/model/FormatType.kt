package dev.dennismcdaid.radio.data.model

import com.squareup.moshi.FromJson
import java.util.*

enum class FormatType(private val rawName: String) {
    AAC("aac"),
    AACP("aacp"),
    MP3("mp3"),
    M4A("audio/mp4a-latm"),
    UNKNOWN("");

    companion object Adapter {

        @FromJson
        fun fromJson(str: String): FormatType {
            return values().firstOrNull {
                it.rawName == str.toLowerCase(Locale.ENGLISH)
            } ?: UNKNOWN
        }
    }
}