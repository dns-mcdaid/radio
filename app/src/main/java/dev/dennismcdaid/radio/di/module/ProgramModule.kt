package dev.dennismcdaid.radio.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import dev.dennismcdaid.radio.di.ViewModelBuilder
import dev.dennismcdaid.radio.di.ViewModelKey
import dev.dennismcdaid.radio.ui.program.ProgramListFragment
import dev.dennismcdaid.radio.ui.program.ProgramListViewModel

@Module
abstract class ProgramModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun programListFragment(): ProgramListFragment

    @Binds
    @IntoMap
    @ViewModelKey(ProgramListViewModel::class)
    abstract fun bindListViewModel(viewModel: ProgramListViewModel): ViewModel
}