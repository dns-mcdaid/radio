package dev.dennismcdaid.radio.data.source.remote

import dev.dennismcdaid.radio.data.model.airnet.AirnetEpisode
import dev.dennismcdaid.radio.data.model.airnet.AirnetProgram
import dev.dennismcdaid.radio.data.model.airnet.AirnetTrack
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface AirnetApi {

    @GET("{$STATION}/programs/{$PROGRAM}")
    suspend fun getProgram(
        @Path(STATION) stationName: String,
        @Path(PROGRAM) programName: String
    ): AirnetProgram

    @GET("{$STATION}/programs/{$PROGRAM}/episodes")
    suspend fun getEpisodes(
        @Path(STATION) stationName: String,
        @Path(PROGRAM) programName: String
    ) : List<AirnetEpisode>

    @GET
    suspend fun getEpisodePlaylist(
        @Url playlistUrl: String
    ): List<AirnetTrack>

    companion object {
        private const val STATION = "station"
        private const val PROGRAM = "program"
        private const val EPISODE = "episode"
    }
}