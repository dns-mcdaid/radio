package dev.dennismcdaid.radio.di

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import dagger.Module
import dagger.Provides
import dev.dennismcdaid.radio.R
import javax.inject.Singleton

@Module
object AudioModule {

    @Provides
    @Singleton
    fun provideExoPlayer(context: Context): ExoPlayer {
        return SimpleExoPlayer.Builder(context).build()
    }

    @Provides
    @Singleton
    fun providePlayerDataSource(context: Context): DefaultDataSourceFactory {
        return DefaultDataSourceFactory(
            context,
            Util.getUserAgent(context, context.getString(R.string.app_name))
        )
    }
}