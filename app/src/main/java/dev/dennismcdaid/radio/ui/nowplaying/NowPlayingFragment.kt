package dev.dennismcdaid.radio.ui.nowplaying

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import coil.api.load
import dev.dennismcdaid.radio.R
import dev.dennismcdaid.radio.databinding.FragmentNowPlayingBinding
import dev.dennismcdaid.radio.ui.base.BaseFragment
import dev.dennismcdaid.radio.ui.main.MainActivity
import dev.dennismcdaid.radio.ui.main.PlayerViewState
import dev.dennismcdaid.radio.ui.main.StreamSource

class NowPlayingFragment : BaseFragment<FragmentNowPlayingBinding>() {
    override val viewModel by viewModels<NowPlayingViewModel> { viewModelFactory }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNowPlayingBinding {
        return FragmentNowPlayingBinding.inflate(inflater, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).toggleHeaderAndFooter(show = false)

        (activity as MainActivity).viewModel.playerState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is PlayerViewState.Active -> {
                    binding.playButton.isEnabled = true
                    val res = if (state.playing) R.drawable.ic_pause_circle else R.drawable.ic_play_circle
                    binding.playButton.setImageResource(res)
                    binding.showName.text = state.name
                    binding.description.text = state.description
                    binding.presenter.text = state.presenter
                    binding.showImage.load(state.image) {
                        placeholder(R.drawable.ic_music_note)
                        error(R.drawable.ic_music_note)
                    }
                }
                is PlayerViewState.Loading -> {
                    binding.showName.setText(PlayerViewState.Loading.message)
                    binding.playButton.isEnabled = false
                }
                is PlayerViewState.Error -> {
                    binding.playButton.isEnabled = true
                    binding.playButton.setImageResource(R.drawable.ic_retry)
                }
            }
        }

        (activity as MainActivity).viewModel.playerStream.observe(viewLifecycleOwner) { source ->
            when (source) {
                StreamSource.Live -> {
                    binding.liveToggle.setText(R.string.on_air)
                    binding.liveToggle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_on_air, 0, 0 ,0)
                }
                is StreamSource.Episode -> {
                    binding.liveToggle.setText(R.string.go_live)
                    binding.liveToggle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_live, 0, 0, 0)
                }
            }
        }

        binding.closeBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.liveToggle.setOnClickListener {
            (activity as MainActivity).viewModel.goLive()
        }

        binding.playButton.setOnClickListener {
            (activity as MainActivity).viewModel.onPlayClicked()
        }
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).toggleHeaderAndFooter(show = true)
    }
}