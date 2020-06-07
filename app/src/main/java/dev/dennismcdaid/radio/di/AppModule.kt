package dev.dennismcdaid.radio.di

import android.app.NotificationManager
import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dev.dennismcdaid.radio.BuildConfig
import dev.dennismcdaid.radio.data.model.EpisodeStatus
import dev.dennismcdaid.radio.data.model.FormatType
import dev.dennismcdaid.radio.data.model.StationType
import dev.dennismcdaid.radio.data.source.AirnetApi
import dev.dennismcdaid.radio.data.source.EmitApi
import dev.dennismcdaid.radio.util.DateFormatter
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module(
    includes = [
        AppModuleBinds::class,
        AudioModule::class
    ]
)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(context: Context) : OkHttpClient {
        return OkHttpClient.Builder()
            .cache(Cache(context.cacheDir, 10 * 1024 * 1024))
            .addInterceptor(ChuckerInterceptor(context))
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(FormatType.Adapter)
            .add(EpisodeStatus.Adapter)
            .add(DateFormatter.Adapter)
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideStationType(): StationType {
        return StationType.PBS
    }

    @Provides
    @Singleton
    fun provideEmitApi(okHttpClient: OkHttpClient, moshi: Moshi): EmitApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.EMIT_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(EmitApi::class.java)
    }
    
    @Provides
    @Singleton
    fun provideAirnetApi(okHttpClient: OkHttpClient, moshi: Moshi): AirnetApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.AIRNET_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(AirnetApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNotificationManager(context: Context): NotificationManager {
        return context.getSystemService(NotificationManager::class.java) as NotificationManager
    }
}