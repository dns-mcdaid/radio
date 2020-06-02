package dev.dennismcdaid.radio.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat

object DateFormatter {

    private const val AM_PM_FORMAT = "hh:mm a"

    private const val ISO_8601 = "yyyy-MM-dd'T'HH:mm:ssZ"

    fun localDateTime(dateStr: String): LocalDateTime {
        val formatter = DateTimeFormat.forPattern(ISO_8601)
        return formatter.parseLocalDateTime(dateStr)
    }

    fun hourAmPm(time: LocalDateTime): String {
        val formatter = DateTimeFormat.forPattern(AM_PM_FORMAT)
        return formatter.print(time)
    }

    object Adapter {
        @FromJson
        fun fromJson(string: String): LocalDateTime {
            return localDateTime(string)
        }

        @ToJson
        fun toJson(dateTime: LocalDateTime): String {
            val formatter = DateTimeFormat.forPattern(ISO_8601)
            return formatter.print(dateTime)
        }
    }
}