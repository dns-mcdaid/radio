package dev.dennismcdaid.radio.ui.program.episode

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import dev.dennismcdaid.radio.databinding.FragmentEpisodeBinding
import dev.dennismcdaid.radio.ui.base.BaseFragment
import dev.dennismcdaid.radio.ui.main.MainActivity
import dev.dennismcdaid.radio.ui.observeEvent

class EpisodeFragment : BaseFragment<FragmentEpisodeBinding>() {
    override val viewModel by viewModels<EpisodeViewModel> { viewModelFactory }
    private val args by navArgs<EpisodeFragmentArgs>()



    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEpisodeBinding {
        return FragmentEpisodeBinding.inflate(inflater, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = TrackAdapter()

        binding.trackList.adapter = adapter

        viewModel.viewStateLiveData.observe(viewLifecycleOwner) {
            (activity as MainActivity).setTitle(it.title)
            binding.description.text = Html.fromHtml(it.description, Html.FROM_HTML_MODE_COMPACT)
            adapter.submitList(it.tracks)
        }
        viewModel.loadEpisode(args.bundle)

        binding.playButton.setOnClickListener {
            viewModel.playEpisode(args.bundle)
        }

        viewModel.playEpisodeEvent.observeEvent(viewLifecycleOwner) {
            (activity as MainActivity).viewModel.playEpisode(it)
        }
    }
}