package dev.dennismcdaid.radio.data

import dev.dennismcdaid.radio.data.model.Episode
import dev.dennismcdaid.radio.data.model.Program
import dev.dennismcdaid.radio.data.model.Station
import kotlinx.coroutines.flow.Flow

interface StationRepository {
    fun getStation(): Flow<Station>

    fun getOnAir(): Flow<List<Episode>>

    fun getSchedule(): Flow<List<Episode>>

    fun getShows(): Flow<List<Program>>

    fun getProgram(programName: String): Flow<Program>

    fun getEpisodes(programName: String): Flow<List<Episode>>

    fun getEpisode(programName: String, timestamp: String): Flow<Episode>
}