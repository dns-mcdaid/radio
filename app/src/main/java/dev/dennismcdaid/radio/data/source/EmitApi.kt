package dev.dennismcdaid.radio.data.source

import dev.dennismcdaid.radio.data.model.emit.EmitEpisode
import dev.dennismcdaid.radio.data.model.emit.EmitProgram
import dev.dennismcdaid.radio.data.model.emit.EmitStation
import retrofit2.http.GET
import retrofit2.http.Path

interface EmitApi {

    @GET("{$STATION}")
    suspend fun getStation(@Path(STATION) stationName: String): EmitStation

    @GET("{$STATION}/on-air")
    suspend fun getOnAir(@Path(STATION) stationName: String): List<EmitEpisode>

    @GET("{$STATION}/schedule")
    suspend fun getSchedule(@Path(STATION) stationName: String): List<EmitEpisode>

    @GET("{$STATION}/shows")
    suspend fun getShows(@Path(STATION) stationName: String): List<EmitProgram>

    @GET("{$STATION}/shows/{$PROGRAM}")
    suspend fun getProgram(
        @Path(STATION) stationName: String,
        @Path(PROGRAM) programName: String
    ) : EmitProgram

    @GET("{$STATION}/shows/{$PROGRAM}/episodes")
    suspend fun getEpisodes(
        @Path(STATION) stationName: String,
        @Path(PROGRAM) programName: String
    ) : List<EmitEpisode>

    @GET("{$STATION}/shows/{$PROGRAM}/episodes/{$TIMESTAMP}")
    suspend fun getEpisode(
        @Path(STATION)stationName: String,
        @Path(PROGRAM) programName: String,
        @Path(TIMESTAMP) timestamp: String
    ) : EmitEpisode

    companion object {
        private const val STATION = "station"
        private const val PROGRAM = "program"
        private const val TIMESTAMP = "timestamp"
    }
}