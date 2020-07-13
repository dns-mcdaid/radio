package dev.dennismcdaid.radio.ui.program.detail

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.api.load
import coil.transform.CircleCropTransformation
import dev.dennismcdaid.radio.R
import dev.dennismcdaid.radio.databinding.FragmentProgramDetailBinding
import dev.dennismcdaid.radio.ui.base.BaseFragment
import dev.dennismcdaid.radio.ui.main.MainActivity
import dev.dennismcdaid.radio.ui.observeEvent
import timber.log.Timber

class ProgramDetailFragment : BaseFragment<FragmentProgramDetailBinding>() {

    override val viewModel by viewModels<ProgramDetailViewModel> { viewModelFactory }

    private val args by navArgs<ProgramDetailFragmentArgs>()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProgramDetailBinding {
        return FragmentProgramDetailBinding.inflate(inflater, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.episodesList.adapter = EpisodeAdapter(viewModel)

        viewModel.viewStateLiveData.observe(viewLifecycleOwner, this::applyViewState)
        viewModel.setSlug(args.slug)

        viewModel.navigateToEpisode.observeEvent(viewLifecycleOwner) {
            findNavController()
                .navigate(ProgramDetailFragmentDirections
                    .actionProgramDetailToEpisode(it)
                )
        }
    }

    private fun applyViewState(viewState: ProgramDetailViewState) {
        binding.loadingSpinner.isVisible = viewState == ProgramDetailViewState.Loading

        when (viewState) {
            is ProgramDetailViewState.Loaded -> {
                (activity as MainActivity).setTitle(viewState.program.programName)
                binding.banner.load(viewState.program.bannerImageUrl) {
                    crossfade(true)
                }
                binding.presenterAvatarContainer.visibility = View.VISIBLE
                binding.presenterAvatar.load(viewState.program.profileImageUrl) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
                binding.presenterText.text = resources.getString(R.string.presenter_with, viewState.program.presenter)
                binding.description.text = Html.fromHtml(viewState.program.longDesc, Html.FROM_HTML_MODE_COMPACT)
                (binding.episodesList.adapter as? EpisodeAdapter)
                    ?.submitList(viewState.program.episodes.sortedByDescending { it.startTime })
            }
            is ProgramDetailViewState.Error -> {
                Timber.w(viewState.message)
            }
        }
    }
}