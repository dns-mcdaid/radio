package dev.dennismcdaid.radio.data.model

import androidx.room.TypeConverter
import dev.dennismcdaid.radio.util.DateFormatter
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat

class DateTypeConverter {

    @TypeConverter
    fun convertFromDate(date: LocalDateTime): String {
        return DateTimeFormat.forPattern(DateFormatter.DOWNLOAD_FORMAT).print(date)
    }

    @TypeConverter
    fun convertToDate(timestamp: String): LocalDateTime {
        return DateTimeFormat.forPattern(DateFormatter.DOWNLOAD_FORMAT).parseLocalDateTime(timestamp)
    }
}