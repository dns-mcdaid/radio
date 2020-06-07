package dev.dennismcdaid.radio.ui.schedule

import dev.dennismcdaid.radio.data.model.emit.EmitEpisode
import org.joda.time.LocalDate

sealed class ScheduleRow : Comparable<ScheduleRow> {

    abstract val date: LocalDate

    override fun compareTo(other: ScheduleRow): Int {
        return date.compareTo(other.date)
    }

    data class Header(override val date: LocalDate): ScheduleRow() {
        val text: String
            get() {
                val today = LocalDate.now()
                return when (date) {
                    LocalDate.now() -> "Today"
                    today.plusDays(1) -> "Tomorrow"
                    else -> date.dayOfWeek().asText
                }
            }
    }
    data class Item(val episode: EmitEpisode): ScheduleRow() {
        override val date: LocalDate = episode.startTime.toLocalDate()
    }
}