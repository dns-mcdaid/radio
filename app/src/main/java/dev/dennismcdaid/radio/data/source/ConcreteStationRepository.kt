package dev.dennismcdaid.radio.data.source

import dev.dennismcdaid.radio.data.StationRepository
import dev.dennismcdaid.radio.data.model.Program
import dev.dennismcdaid.radio.data.model.emit.EmitEpisode
import dev.dennismcdaid.radio.data.model.emit.EmitProgram
import dev.dennismcdaid.radio.data.model.emit.EmitStation
import dev.dennismcdaid.radio.data.model.StationType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
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
        return flow { emit(emitApi.getEpisodes(stationType.emitCallSign, slug)) }
            .catch { emit(emptyList()) }
            .combine(airnetProgram) { episodes, program ->
                Program(
                    program.slug,
                    program.name,
                    program.broadcasters,
                    program.gridDescription,
                    program.description,
                    program.profileImageUrl,
                    program.bannerImageUrl,
                    program.facebookPage ?: "",
                    program.twitterHandle ?: "",
                    episodes
                )
            }
    }

    override fun getEpisodes(programName: String): Flow<List<EmitEpisode>> {
        TODO("Not yet implemented")
    }

    override fun getEpisode(programName: String, timestamp: String): Flow<EmitEpisode> {
        TODO("Not yet implemented")
    }

}