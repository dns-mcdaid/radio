package dev.dennismcdaid.radio.ui.saved

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.dennismcdaid.radio.data.model.EpisodeDownload
import dev.dennismcdaid.radio.databinding.ItemEpisodeBinding
import dev.dennismcdaid.radio.ui.util.diffCallback

class SavedEpisodeAdapter(private val viewModel: SavedViewModel) : ListAdapter<EpisodeDownload, SavedEpisodeAdapter.ViewHolder>(
    diffCallback { oldItem, newItem -> oldItem.id == newItem.id }
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
        fun bind(episode: EpisodeDownload, viewModel: SavedViewModel) {
            binding.episodeDateTime.text = episode.name
            binding.episodeTitle.text = episode.presenter
            binding.root.setOnClickListener {
                viewModel.onEpisodeClicked(episode)
            }
        }
    }
}