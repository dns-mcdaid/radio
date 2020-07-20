package dev.dennismcdaid.radio.ui.program.episode

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dev.dennismcdaid.radio.R
import dev.dennismcdaid.radio.databinding.FragmentEpisodeBinding
import dev.dennismcdaid.radio.ui.base.BaseFragment
import dev.dennismcdaid.radio.ui.main.MainActivity
import dev.dennismcdaid.radio.ui.observeEvent

class EpisodeFragment : BaseFragment<FragmentEpisodeBinding>() {
    override val viewModel by viewModels<EpisodeViewModel> { viewModelFactory }
    private val args by navArgs<EpisodeFragmentArgs>()
    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

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

            binding.downloadButton.isClickable = !it.loading
            binding.playButton.isEnabled = !it.loading

            binding.downloadButton.setImageResource(if (it.downloaded) R.drawable.ic_downloaded_circle else R.drawable.ic_download_circle)
        }
        viewModel.loadEpisode(args.bundle)

        binding.playButton.setOnClickListener {
            viewModel.playEpisode(args.bundle)
        }

        binding.downloadButton.setOnClickListener {
            if (hasPermissions()) {
                viewModel.onDownloadClicked(args.bundle)
            } else {
                requestPermissions(permissions, REQUEST_CODE)
            }
        }

        viewModel.playEpisodeEvent.observeEvent(viewLifecycleOwner) {
            (activity as MainActivity).viewModel.playEpisode(it)
        }
    }

    private fun hasPermissions(): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            viewModel.onDownloadClicked(args.bundle)
        } else {
            Snackbar.make(binding.root, R.string.download_permission_needed, Snackbar.LENGTH_LONG).show()
        }
    }

    companion object {
        private const val REQUEST_CODE = 1
    }
}