package dev.dennismcdaid.radio

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dev.dennismcdaid.radio.di.DaggerAppComponent
import timber.log.Timber

class RadioApp : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(applicationContext)
    }
}