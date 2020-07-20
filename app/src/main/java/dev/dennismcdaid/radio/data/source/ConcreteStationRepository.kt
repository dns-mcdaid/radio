package dev.dennismcdaid.radio.data.source

import dev.dennismcdaid.radio.data.StationRepository
import dev.dennismcdaid.radio.data.model.Episode
import dev.dennismcdaid.radio.data.model.Program
import dev.dennismcdaid.radio.data.model.StationType
import dev.dennismcdaid.radio.data.model.airnet.AirnetEpisode
import dev.dennismcdaid.radio.data.model.airnet.AirnetTrack
import dev.dennismcdaid.radio.data.model.emit.EmitEpisode
import dev.dennismcdaid.radio.data.model.emit.EmitProgram
import dev.dennismcdaid.radio.data.model.emit.EmitStation
import dev.dennismcdaid.radio.util.DateFormatter
import kotlinx.coroutines.flow.*
import org.joda.time.LocalDateTime
import javax.inject.Inject

class ConcreteStationRepository @Inject constructor(
    private val emitApi: EmitApi,
    private val airnetApi: AirnetApi,
    private val stationType: StationType
): StationRepository {

    override fun getStation(): Flow<EmitStation> {
        return flow {
            val result = emitApi.getStation(stationType.emitCallSign)
            emit(result)
        }
    }

    override fun getOnAir(): Flow<List<EmitEpisode>> {
        return flow {
            val result = emitApi.getOnAir(stationType.emitCallSign)
            emit(result)
        }
    }

    override fun getSchedule(): Flow<List<EmitEpisode>> {
        return flow {
            val result = emitApi.getSchedule(stationType.emitCallSign)
            emit(result)
        }
    }

    override fun getShows(): Flow<List<EmitProgram>> {
        return flow {
            val result = emitApi.getShows(stationType.emitCallSign)
            emit(result)
        }
    }

    override fun getProgram(slug: String): Flow<Program> {
        val airnetProgram = flow {
            emit(airnetApi.getProgram(stationType.airnetCallSign, slug))
        }
        return flow { emit(airnetApi.getEpisodes(stationType.airnetCallSign, slug)) }
            .catch { emit(emptyList()) }
            .map { episodes ->
                episodes.map(this::mapEpisode)
            }
            .combine(airnetProgram) { episodes, program ->
                Program(
                    program.slug,
                    program.name,
                    program.broadcasters,
                    program.gridDescription,
                    program.description ?: program.gridDescription,
                    program.profileImageUrl,
                    program.bannerImageUrl ?: "https://amrap-pages-image.s3.amazonaws.com/banner/6878.jpg?cacbeb=8577515",
                    program.facebookPage ?: "",
                    program.twitterHandle ?: "",
                    episodes
                )
            }
    }

    override fun getTracks(playlistUrl: String): Flow<List<AirnetTrack>> {
        return flow {
            emit(airnetApi.getEpisodePlaylist(playlistUrl))
        }
    }

    override fun getEpisode(programSlug: String, airDateTime: LocalDateTime): Flow<EmitEpisode> {
        return flow {
            val pathFormatted = DateFormatter.episodeDownloadPath(airDateTime)
            emit(emitApi.getEpisode(stationType.emitCallSign, programSlug, pathFormatted))
        }
    }

    private fun mapEpisode(airnetEpisode: AirnetEpisode): Episode {
        return Episode(
            title = airnetEpisode.title ?: "",
            startTime = airnetEpisode.startTime,
            endTime = airnetEpisode.endTime,
            timestamp = airnetEpisode.timestamp,
            totalDuration = airnetEpisode.duration,
            imageUrl = airnetEpisode.imageUrl,
            description = airnetEpisode.description,
            playlistUrl = airnetEpisode.playlistUrl
        )
    }

}