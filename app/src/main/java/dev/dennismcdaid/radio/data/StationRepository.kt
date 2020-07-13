package dev.dennismcdaid.radio.data

import dev.dennismcdaid.radio.data.model.Episode
import dev.dennismcdaid.radio.data.model.Program
import dev.dennismcdaid.radio.data.model.airnet.AirnetTrack
import dev.dennismcdaid.radio.data.model.emit.EmitEpisode
import dev.dennismcdaid.radio.data.model.emit.EmitProgram
import dev.dennismcdaid.radio.data.model.emit.EmitStation
import kotlinx.coroutines.flow.Flow

interface StationRepository {
    fun getStation(): Flow<EmitStation>

    fun getOnAir(): Flow<List<EmitEpisode>>

    fun getSchedule(): Flow<List<EmitEpisode>>

    fun getShows(): Flow<List<EmitProgram>>

    fun getProgram(slug: String): Flow<Program>

    fun getTracks(playlistUrl: String): Flow<List<AirnetTrack>>
}