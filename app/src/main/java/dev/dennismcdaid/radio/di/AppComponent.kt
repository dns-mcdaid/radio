package dev.dennismcdaid.radio.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dev.dennismcdaid.radio.RadioApp
import dev.dennismcdaid.radio.di.module.AudioModule
import dev.dennismcdaid.radio.di.module.MainModule
import dev.dennismcdaid.radio.di.module.ProgramModule
import dev.dennismcdaid.radio.di.module.ScheduleModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        AudioModule::class,
        ProgramModule::class,
        MainModule::class,
        ScheduleModule::class
    ]
)
interface AppComponent : AndroidInjector<RadioApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}