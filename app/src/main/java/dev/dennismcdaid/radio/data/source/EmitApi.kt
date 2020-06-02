package dev.dennismcdaid.radio.data.source

import dev.dennismcdaid.radio.data.model.Episode
import dev.dennismcdaid.radio.data.model.Program
import dev.dennismcdaid.radio.data.model.Station
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface EmitApi {

    @GET("{$STATION}")
    suspend fun getStation(@Path(STATION) stationName: String): Station

    @GET("{$STATION}/on-air")
    suspend fun getOnAir(@Path(STATION) stationName: String): List<Episode>

    @GET("{$STATION}/schedule")
    suspend fun getSchedule(@Path(STATION) stationName: String): List<Episode>

    @GET("{$STATION}/shows")
    suspend fun getShows(@Path(STATION) stationName: String): List<Program>

    @GET("{$STATION}/shows/{$PROGRAM}")
    suspend fun getProgram(
        @Path(STATION) stationName: String,
        @Path(PROGRAM) programName: String
    ) : Program

    @GET("{$STATION}/shows/{$PROGRAM}/episodes")
    suspend fun getEpisodes(
        @Path(STATION) stationName: String,
        @Path(PROGRAM) programName: String
    ) : List<Episode>

    @GET("{$STATION}/shows/{$PROGRAM}/$TIMESTAMP")
    suspend fun getEpisode(
        @Path(STATION)stationName: String,
        @Path(PROGRAM) programName: String,
        @Path(TIMESTAMP) timestamp: String
    ) : Episode

    companion object {
        private const val STATION = "station"
        private const val PROGRAM = "program"
        private const val TIMESTAMP = "timestamp"
    }
}