package dev.dennismcdaid.radio.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import dev.dennismcdaid.radio.di.ViewModelBuilder
import dev.dennismcdaid.radio.di.ViewModelKey
import dev.dennismcdaid.radio.ui.main.MainActivity
import dev.dennismcdaid.radio.ui.main.MainViewModel

@Module
abstract class MainModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun mainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}