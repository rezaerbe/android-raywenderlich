package com.erbe.findmylaunch.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.erbe.findmylaunch.R
import com.erbe.findmylaunch.appComponent
import com.erbe.findmylaunch.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = _binding!!

    private val viewModel: DetailsViewModel by viewModels {
        appComponent(requireContext()).detailsViewModelFactory()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        viewModel.fetchLaunchDetails(requireArguments().getString(keyLaunchName)!!)

        viewModel.launch.observe(viewLifecycleOwner) { launch ->
            binding.name.text = launch.name
            binding.details.text = launch.details.ifBlank { getString(R.string.no_details_text) }
            binding.toolbar.title = launch.name
        }

        return binding.root
    }

    companion object {

        const val keyLaunchName: String = "launchName"

        fun newInstance(launchName: String): DetailsFragment {
            return DetailsFragment().apply {
                arguments = bundleOf(keyLaunchName to launchName)
            }
        }
    }
}