package dev.dennismcdaid.radio.data.source

import dev.dennismcdaid.radio.data.model.airnet.AirnetProgram
import dev.dennismcdaid.radio.data.model.airnet.AirnetTrack
import retrofit2.http.GET
import retrofit2.http.Path

interface AirnetApi {

    @GET("{$STATION}/programs/{$PROGRAM}")
    suspend fun getProgram(
        @Path(STATION) stationName: String,
        @Path(PROGRAM) programName: String
    ): AirnetProgram

    @GET("{$STATION}/programs/{$PROGRAM}/episodes/{$EPISODE}/playlists")
    suspend fun getEpisodePlaylist(
        @Path(STATION) stationName: String,
        @Path(PROGRAM) programName: String,
        @Path(EPISODE) episodeName: String
    ): List<AirnetTrack>

    companion object {
        private const val STATION = "station"
        private const val PROGRAM = "program"
        private const val EPISODE = "episode"
    }
}