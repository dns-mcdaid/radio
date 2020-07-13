package dev.dennismcdaid.radio.ui.program.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.dennismcdaid.radio.data.model.Episode
import dev.dennismcdaid.radio.databinding.ItemEpisodeBinding
import dev.dennismcdaid.radio.ui.util.diffCallback
import dev.dennismcdaid.radio.util.DateFormatter

class EpisodeAdapter(private val viewModel: ProgramDetailViewModel) : ListAdapter<Episode, EpisodeAdapter.ViewHolder>(
    diffCallback { oldItem, newItem -> oldItem.timestamp == newItem.timestamp }
) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), viewModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val lf = LayoutInflater.from(parent.context)
        val binding = ItemEpisodeBinding.inflate(lf, parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(private val binding: ItemEpisodeBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(episode: Episode, viewModel: ProgramDetailViewModel) {
            binding.root.isClickable = !episode.playlistUrl.isNullOrEmpty()
            binding.chevron.isVisible = !episode.playlistUrl.isNullOrEmpty()

            binding.episodeDateTime.text = DateFormatter.episodeDateTime(episode.startTime)
            episode.title.takeIf { it.isNotEmpty() }?.let {
                binding.episodeTitle.text = it
                binding.episodeTitle.isVisible = true
            }

            if (!episode.playlistUrl.isNullOrEmpty()) {
                binding.root.setOnClickListener {
                    viewModel.onEpisodeClicked(episode)
                }
            }
        }
    }
}