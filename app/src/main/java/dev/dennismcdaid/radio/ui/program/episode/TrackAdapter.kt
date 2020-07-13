package dev.dennismcdaid.radio.ui.program.episode

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.dennismcdaid.radio.data.model.airnet.AirnetTrack
import dev.dennismcdaid.radio.databinding.ItemTrackBinding
import dev.dennismcdaid.radio.ui.util.diffCallback

class TrackAdapter : ListAdapter<AirnetTrack, TrackAdapter.ViewHolder>(
    diffCallback { oldItem, newItem -> oldItem.id == newItem.id }
) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val lf = LayoutInflater.from(parent.context)
        val binding = ItemTrackBinding.inflate(lf, parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(private val binding: ItemTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(track: AirnetTrack) {
            binding.trackName.text = track.title
            binding.artist.text = track.artist
        }
    }
}