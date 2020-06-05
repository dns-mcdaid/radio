package dev.dennismcdaid.radio.ui.program

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import dagger.android.support.DaggerFragment
import dev.dennismcdaid.radio.R
import dev.dennismcdaid.radio.databinding.FragmentProgramListBinding
import dev.dennismcdaid.radio.ui.util.hideKeyboard
import javax.inject.Inject

class ProgramListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var binding: FragmentProgramListBinding? = null
    private val viewModel by viewModels<ProgramListViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProgramListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = ProgramListAdapter(viewModel)
        binding?.programList?.adapter = adapter

        viewModel.shows.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding?.emptyResults?.isVisible = it.isEmpty()
        }

        viewModel.showClear.observe(viewLifecycleOwner) { showClear ->
            val drawable = if (showClear) resources.getDrawable(R.drawable.ic_clear, context?.theme) else null
            binding?.programSearchLayout?.endIconDrawable = drawable
            if (!showClear) {
                binding?.programSearchInput?.text?.clear()
                binding?.programSearchInput?.clearFocus()
                hideKeyboard()
            }
        }

        binding?.programSearchInput?.doOnTextChanged { text, _, _, _ ->
            viewModel.observeInput(text?.toString() ?: "")
        }

        binding?.programSearchLayout?.setEndIconOnClickListener {
            viewModel.clear()
        }
    }
}