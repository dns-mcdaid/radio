package dev.dennismcdaid.radio.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.dennismcdaid.radio.BuildConfig
import dev.dennismcdaid.radio.data.model.EpisodeDownload

@Database(
    entities = [
        EpisodeDownload::class
    ],
    version = 1,
    exportSchema = false
)
abstract class RadioDatabase : RoomDatabase() {

    abstract fun episodesDao(): EpisodeDownloadDao

    companion object {
        const val DB_NAME = "${BuildConfig.STATION}-radio.db"
    }
}