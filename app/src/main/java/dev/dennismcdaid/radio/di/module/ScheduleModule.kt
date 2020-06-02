package dev.dennismcdaid.radio.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import dev.dennismcdaid.radio.di.ViewModelBuilder
import dev.dennismcdaid.radio.di.ViewModelKey
import dev.dennismcdaid.radio.ui.schedule.ScheduleFragment
import dev.dennismcdaid.radio.ui.schedule.ScheduleViewModel

@Module
abstract class ScheduleModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun scheduleFragment(): ScheduleFragment

    @Binds
    @IntoMap
    @ViewModelKey(ScheduleViewModel::class)
    abstract fun bindScheduleViewModel(scheduleViewModel: ScheduleViewModel): ViewModel
}