package dev.dennismcdaid.radio.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.dennismcdaid.radio.data.model.EpisodeDownload
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodeDownloadDao {

    @Query("SELECT * FROM episodes ORDER BY recorded_at DESC")
    fun getDownloadedEpisodes(): Flow<List<EpisodeDownload>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisode(episodeDownload: EpisodeDownload)

    @Query("DELETE FROM episodes")
    suspend fun deleteAllEpisodes()
}