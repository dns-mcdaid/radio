package dev.dennismcdaid.radio.data.source

import dev.dennismcdaid.radio.data.model.airnet.AirnetProgram
import retrofit2.http.GET
import retrofit2.http.Path

interface AirnetApi {

    @GET("{$STATION}/programs/{$PROGRAM}")
    suspend fun getProgram(
        @Path(STATION) stationName: String,
        @Path(PROGRAM) programName: String
    ): AirnetProgram

    companion object {
        private const val STATION = "station"
        private const val PROGRAM = "program"
    }
}