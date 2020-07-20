package dev.dennismcdaid.radio.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import dagger.android.support.DaggerFragment
import dev.dennismcdaid.radio.databinding.FragmentScheduleBinding
import dev.dennismcdaid.radio.ui.base.BaseFragment
import dev.dennismcdaid.radio.ui.observeEvent
import javax.inject.Inject

class ScheduleFragment : BaseFragment<FragmentScheduleBinding>() {
    override val viewModel by viewModels<ScheduleViewModel> { viewModelFactory }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentScheduleBinding {
        return FragmentScheduleBinding.inflate(inflater, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = ScheduleAdapter(viewModel)
        binding.scheduleList.adapter = adapter

        viewModel.schedule.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.navigateToProgram.observeEvent(viewLifecycleOwner) {
            findNavController().navigate(
                ScheduleFragmentDirections
                    .actionScheduleToProgramDetail(it)
            )
        }
    }
}