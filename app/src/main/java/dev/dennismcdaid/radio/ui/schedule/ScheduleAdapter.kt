package dev.dennismcdaid.radio.ui.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.dennismcdaid.radio.data.model.emit.EmitEpisode
import dev.dennismcdaid.radio.databinding.ItemShowBinding
import dev.dennismcdaid.radio.ui.util.diffCallback
import dev.dennismcdaid.radio.ui.util.loadPresenter
import dev.dennismcdaid.radio.util.DateFormatter

class ScheduleAdapter(private val viewModel: ScheduleViewModel) : ListAdapter<ScheduleRow, ScheduleAdapter.ViewHolder>(
    diffCallback { oldItem, newItem -> oldItem == newItem }
) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val lf = LayoutInflater.from(parent.context)
        val binding = ItemShowBinding.inflate(lf, parent, false)
        return ViewHolder(binding, viewModel)
    }

    class ViewHolder(private val binding: ItemShowBinding, private val viewModel: ScheduleViewModel) : RecyclerView.ViewHolder(binding.root) {

        fun bind(row: ScheduleRow) = with(binding) {
            when (row) {
                is ScheduleRow.Item -> {
                    headerText.isVisible = false
                    cardView.isVisible = true
                    val episode = row.episode
                    presenterAvatar.loadPresenter(episode.program, circleCrop = true)
                    title.text = episode.program.name
                    presenterName.text = episode.program.presenter
                    description.text = episode.program.description ?: "n/a"
                    timeText.text = episode.shortTime()
                    cardView.setOnClickListener {
                        viewModel.onProgramClicked(episode.program)
                    }
                }
                is ScheduleRow.Header -> {
                    headerText.isVisible = true
                    cardView.isVisible = false
                    headerText.text = row.text
                }
            }
        }

        private fun EmitEpisode.shortTime(): String {
            return listOf(startTime, endTime).map { DateFormatter.hourAmPm(it) }
                .reduce { acc, s ->
                    "$acc - $s"
                }
        }
    }


}