package com.erbe.findmylaunch.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.erbe.findmylaunch.R
import com.erbe.findmylaunch.appComponent
import com.erbe.findmylaunch.databinding.FragmentSearchBinding
import com.erbe.findmylaunch.details.DetailsFragment

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!

    private val viewModel: SearchViewModel by viewModels {
        appComponent(requireContext()).searchViewModelFactory()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        setupSearchResults()
        setupSearchField()
        setupBackNavigation()

        return binding.root
    }

    private fun setupSearchField() {
        binding.searchBox.addTextChangedListener { query ->
            viewModel.search(query)
        }
    }

    private fun setupBackNavigation() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (!binding.searchBox.text.isNullOrBlank()) {
                binding.searchBox.text = null
            } else {
                isEnabled = false
                requireActivity().onBackPressed()
            }
        }
    }

    private fun setupSearchResults() {
        val searchResultsAdapter = SearchResultsAdapter { launch ->
            parentFragmentManager.commit {
//                setCustomAnimations(
//                    R.anim.fragment_fade_enter,
//                    R.anim.fragment_fade_exit,
//                    R.anim.fragment_fade_enter,
//                    R.anim.fragment_fade_exit
//                )
                replace(R.id.fragmentContainer, DetailsFragment.newInstance(launch.name))
                addToBackStack(launch.name)
            }
        }

        binding.searchResults.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = searchResultsAdapter
        }

        viewModel.searchResults.observe(viewLifecycleOwner) { searchResults ->
            searchResultsAdapter.submitList(searchResults)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}