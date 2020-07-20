package dev.dennismcdaid.radio.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import dev.dennismcdaid.radio.di.ViewModelBuilder
import dev.dennismcdaid.radio.di.ViewModelKey
import dev.dennismcdaid.radio.service.DownloadBroadcastReceiver
import dev.dennismcdaid.radio.ui.main.MainActivity
import dev.dennismcdaid.radio.ui.main.MainViewModel
import dev.dennismcdaid.radio.ui.nowplaying.NowPlayingFragment
import dev.dennismcdaid.radio.ui.nowplaying.NowPlayingViewModel
import dev.dennismcdaid.radio.ui.saved.SavedFragment
import dev.dennismcdaid.radio.ui.saved.SavedViewModel

@Module
abstract class MainModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun mainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun nowPlayingFragment(): NowPlayingFragment

    @Binds
    @IntoMap
    @ViewModelKey(NowPlayingViewModel::class)
    abstract fun bindNowPlayingViewModel(viewModel: NowPlayingViewModel): ViewModel

    @ContributesAndroidInjector
    internal abstract fun downloadReceiver(): DownloadBroadcastReceiver

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun savedFragment(): SavedFragment

    @Binds
    @IntoMap
    @ViewModelKey(SavedViewModel::class)
    abstract fun bindSavedViewModel(viewModel: SavedViewModel): ViewModel
}