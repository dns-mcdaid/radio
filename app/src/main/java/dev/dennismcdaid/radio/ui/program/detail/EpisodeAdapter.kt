package dev.dennismcdaid.radio.ui.program.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.dennismcdaid.radio.data.model.emit.EmitEpisode
import dev.dennismcdaid.radio.databinding.ItemEpisodeBinding
import dev.dennismcdaid.radio.ui.util.diffCallback
import dev.dennismcdaid.radio.util.DateFormatter

class EpisodeAdapter : ListAdapter<EmitEpisode, EpisodeAdapter.ViewHolder>(
    diffCallback { oldItem, newItem -> oldItem.timestamp == newItem.timestamp }
) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val lf = LayoutInflater.from(parent.context)
        val binding = ItemEpisodeBinding.inflate(lf, parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(private val binding: ItemEpisodeBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(episode: EmitEpisode) {
            binding.episodeTitle.text = DateFormatter.episode(episode.startTime)
        }
    }
}