package dev.dennismcdaid.radio.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.joda.time.LocalDateTime
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat

object DateFormatter {

    private const val AM_PM_FORMAT = "hh:mm a"

    private const val ISO_8601 = "yyyy-MM-dd'T'HH:mm:ssZ"

    private const val HMS = "HH:mm:ss"

    private const val TEXT_DATE = "EEE MMM dd yyyy"

    const val DOWNLOAD_FORMAT = "YYYYMMddHHmm"

    fun localDateTime(dateStr: String): LocalDateTime {
        val formatter = DateTimeFormat.forPattern(ISO_8601)
        return formatter.parseLocalDateTime(dateStr)
    }

    fun hourAmPm(time: LocalDateTime): String {
        val formatter = DateTimeFormat.forPattern(AM_PM_FORMAT)
        return formatter.print(time)
    }

    fun localTime(string: String): LocalTime {
        val formatter = DateTimeFormat.forPattern(HMS)
        return formatter.parseLocalTime(string)
    }

    fun hourAmPm(time: LocalTime): String {
        val formatter = DateTimeFormat.forPattern(AM_PM_FORMAT)
        return formatter.print(time)
    }

    fun episodeDate(localDateTime: LocalDateTime): String {
        return DateTimeFormat.forPattern(TEXT_DATE).print(localDateTime)
    }

    fun episodeDownloadPath(localDateTime: LocalDateTime): String {
        return DateTimeFormat.forPattern(DOWNLOAD_FORMAT).print(localDateTime)
    }

    fun episodeDateTime(localDateTime: LocalDateTime): String {
        val date = DateTimeFormat.forPattern(TEXT_DATE).print(localDateTime)
        val time = DateTimeFormat.forPattern(AM_PM_FORMAT).print(localDateTime)
        return "$date at $time"
    }

    object LocalDateTimeAdapter {
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

    object LocalTimeAdapter {
        @FromJson
        fun fromJson(string: String): LocalTime {
            return localTime(string)
        }

        @ToJson
        fun toJson(localTime: LocalTime): String {
            val formatter = DateTimeFormat.forPattern(HMS)
            return formatter.print(localTime)
        }
    }
}