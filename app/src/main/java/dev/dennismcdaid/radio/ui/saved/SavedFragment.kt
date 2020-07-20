package dev.dennismcdaid.radio.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import dev.dennismcdaid.radio.databinding.FragmentSavedBinding
import dev.dennismcdaid.radio.ui.base.BaseFragment
import dev.dennismcdaid.radio.ui.base.BaseViewModel
import dev.dennismcdaid.radio.ui.observeEvent

class SavedFragment : BaseFragment<FragmentSavedBinding>() {

    override val viewModel by viewModels<SavedViewModel> { viewModelFactory }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSavedBinding {
        return FragmentSavedBinding.inflate(inflater, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = SavedEpisodeAdapter(viewModel)

        binding.savedList.adapter = adapter

        viewModel.downloadedEpisodes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.navigateToEpisode.observeEvent(viewLifecycleOwner) {
            findNavController()
                .navigate(SavedFragmentDirections.actionSavedToEpisode(it))
        }
    }
}