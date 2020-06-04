package dev.dennismcdaid.radio.ui.program

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import dev.dennismcdaid.radio.R
import dev.dennismcdaid.radio.data.model.Program
import dev.dennismcdaid.radio.databinding.ItemProgramBinding
import dev.dennismcdaid.radio.ui.util.diffCallback

class ProgramListAdapter(private val viewModel: ProgramListViewModel) : ListAdapter<Program, ProgramListAdapter.ViewHolder>(
    diffCallback { oldItem, newItem -> oldItem.name == newItem.name }
) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), viewModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val lf = LayoutInflater.from(parent.context)
        val binding = ItemProgramBinding.inflate(lf, parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(private val binding: ItemProgramBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(program: Program, viewModel: ProgramListViewModel) {
            binding.presenterAvatar.load(program.imageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_sound)
                error(R.drawable.ic_sound)
            }
            binding.programName.text = program.name

            binding.programCard.setOnClickListener {
                viewModel.onProgramClicked(program)
            }
        }
    }
}