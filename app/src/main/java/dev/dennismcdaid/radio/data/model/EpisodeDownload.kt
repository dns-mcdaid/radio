package dev.dennismcdaid.radio.data.model

import androidx.room.*
import dev.dennismcdaid.radio.data.model.emit.EmitEpisode
import org.joda.time.LocalDateTime
import java.util.*

@TypeConverters(
    value = [
        DateTypeConverter::class
    ]
)
@Entity(tableName = "episodes")
data class EpisodeDownload(
    @PrimaryKey
    var id: String,
    var path: String,

    var name: String,
    @ColumnInfo(name = "program_slug")
    var programSlug: String,
    @ColumnInfo(name = "program_name")
    var programName: String,
    var presenter: String,

    var description: String,
    var playlistUrl: String,

    @ColumnInfo(name = "recorded_at")
    var recordedAt: LocalDateTime
) {

    @Ignore
    constructor(
        fileName: String,
        slug: String,
        recordedAt: LocalDateTime,
        description: String,
        playlistUrl: String,
        episode: EmitEpisode
    ) : this(
        UUID.randomUUID().toString(),
        fileName, episode.name, slug, episode.program.name, episode.program.presenter, description, playlistUrl, recordedAt
    )
}