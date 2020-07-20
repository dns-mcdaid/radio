package dev.dennismcdaid.radio.di

import dagger.Binds
import dagger.Module
import dev.dennismcdaid.radio.data.StationRepository
import dev.dennismcdaid.radio.data.source.ConcreteStationRepository
import dev.dennismcdaid.radio.service.ConcreteEpisodeDownloader
import dev.dennismcdaid.radio.service.EpisodeDownloader

@Module
abstract class AppModuleBinds {
    @Binds
    abstract fun bindStationRepository(repo: ConcreteStationRepository): StationRepository

    @Binds
    abstract fun bindDownloadService(service: ConcreteEpisodeDownloader): EpisodeDownloader
}