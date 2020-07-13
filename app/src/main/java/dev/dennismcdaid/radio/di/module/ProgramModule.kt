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
import dev.dennismcdaid.radio.ui.program.detail.ProgramDetailFragment
import dev.dennismcdaid.radio.ui.program.detail.ProgramDetailViewModel
import dev.dennismcdaid.radio.ui.program.episode.EpisodeFragment
import dev.dennismcdaid.radio.ui.program.episode.EpisodeViewModel

@Module
abstract class ProgramModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun programListFragment(): ProgramListFragment

    @Binds
    @IntoMap
    @ViewModelKey(ProgramListViewModel::class)
    abstract fun bindListViewModel(viewModel: ProgramListViewModel): ViewModel

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun programDetailFragment(): ProgramDetailFragment

    @Binds
    @IntoMap
    @ViewModelKey(ProgramDetailViewModel::class)
    abstract fun bindDetailViewModel(viewModel: ProgramDetailViewModel): ViewModel

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun episodeFragment(): EpisodeFragment


    @Binds
    @IntoMap
    @ViewModelKey(EpisodeViewModel::class)
    abstract fun bindEpisodeViewModel(viewModel: EpisodeViewModel): ViewModel
}