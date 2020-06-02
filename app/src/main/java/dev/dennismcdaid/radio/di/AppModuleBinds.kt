package dev.dennismcdaid.radio.di

import dagger.Binds
import dagger.Module
import dev.dennismcdaid.radio.data.StationRepository
import dev.dennismcdaid.radio.data.source.ConcreteStationRepository

@Module
abstract class AppModuleBinds {
    @Binds
    abstract fun bindStationRepository(repo: ConcreteStationRepository): StationRepository
}