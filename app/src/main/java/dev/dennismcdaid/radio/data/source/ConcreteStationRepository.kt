package dev.dennismcdaid.radio.data.source

import dev.dennismcdaid.radio.data.StationRepository
import dev.dennismcdaid.radio.data.model.Episode
import dev.dennismcdaid.radio.data.model.Program
import dev.dennismcdaid.radio.data.model.Station
import dev.dennismcdaid.radio.di.StationName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ConcreteStationRepository @Inject constructor(
    private val emitApi: EmitApi,
    @StationName private val stationName: String
): StationRepository {

    override fun getStation(): Flow<Station> {
        return flow {
            val result = emitApi.getStation(stationName)
            emit(result)
        }
    }

    override fun getOnAir(): Flow<List<Episode>> {
        return flow {
            val result = emitApi.getOnAir(stationName)
            emit(result)
        }
    }

    override fun getSchedule(): Flow<List<Episode>> {
        return flow {
            val result = emitApi.getSchedule(stationName)
            emit(result)
        }
    }

    override fun getShows(): Flow<List<Program>> {
        return flow {
            val result = emitApi.getShows(stationName)
            emit(result)
        }
    }

    override fun getProgram(programName: String): Flow<Program> {
        TODO("Not yet implemented")
    }

    override fun getEpisodes(programName: String): Flow<List<Episode>> {
        TODO("Not yet implemented")
    }

    override fun getEpisode(programName: String, timestamp: String): Flow<Episode> {
        TODO("Not yet implemented")
    }

}