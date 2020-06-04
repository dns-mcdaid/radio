package dev.dennismcdaid.radio.ui.program

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import dagger.android.support.DaggerFragment
import dev.dennismcdaid.radio.databinding.FragmentProgramListBinding
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
        }
    }
}