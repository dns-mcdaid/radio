package dev.dennismcdaid.radio.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.dennismcdaid.radio.service.AudioPlayerService

@Module
abstract class AudioModule {

    @ContributesAndroidInjector
    abstract fun provideAudioPlayerService(): AudioPlayerService
}