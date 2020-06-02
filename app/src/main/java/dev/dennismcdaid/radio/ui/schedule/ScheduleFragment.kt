package dev.dennismcdaid.radio.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import dagger.android.support.DaggerFragment
import dev.dennismcdaid.radio.databinding.ScheduleFragmentBinding
import javax.inject.Inject

class ScheduleFragment : DaggerFragment() {

    companion object {
        fun newInstance() = ScheduleFragment()
        const val TAG = "schedule_fragment"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ScheduleViewModel> { viewModelFactory }

    private var binding: ScheduleFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ScheduleFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = ScheduleAdapter()
        binding?.scheduleList?.adapter = adapter

        viewModel.schedule.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}