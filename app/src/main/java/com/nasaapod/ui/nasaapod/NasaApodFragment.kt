package com.nasaapod.ui.nasaapod

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nasaapod.R
import com.nasaapod.databinding.NasaApodFragmentBinding
import com.nasaapod.utils.Event
import com.nasaapod.utils.Resource
import com.nasaapod.utils.showCalender
import com.nasaapod.utils.showSnackBarError
import kotlinx.coroutines.flow.collect

class NasaApodFragment : Fragment(R.layout.nasa_apod_fragment) {

    private val viewModel: NasaApodViewModel by viewModels()

    private var _binding: NasaApodFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainAdapter: NasaApodAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        _binding = NasaApodFragmentBinding.bind(view)
        setupBinding()
        setupObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.menu_refresh -> {
                viewModel.refreshData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadData()
    }

    private fun setupBinding() {
        mainAdapter = NasaApodAdapter { apodEntity ->
            (requireActivity() as NasaApodActivity).navController.navigate(
                R.id.action_ApodActivityToDetailFragment,
                bundleOf("apod" to apodEntity)
            )
        }
        binding.apodSwipeRefreshLayout.setOnRefreshListener { viewModel.refreshData() }
        binding.apodRecyclerView.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0 || dy < 0 && binding.fabButton.isShown)
                        binding.fabButton.hide()
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE)
                        binding.fabButton.show()
                    super.onScrollStateChanged(recyclerView, newState)
                }
            })
        }
        binding.fabButton.setOnClickListener {
            childFragmentManager.showCalender {
                viewModel.updateDate(it)
            }
        }
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.also {
            it.launchWhenStarted {
                viewModel.errorEvents.collect { event ->
                    when (event) {
                        is Event.ShowErrorMessage -> showSnackBarError(
                            getString(
                                R.string.something_went_wrong,
                                event.error.localizedMessage
                                    ?: getString(R.string.something_went_wrong)
                            )
                        )
                    }
                }
            }
            it.launchWhenStarted {
                viewModel.lists.collect { resource ->
                    val result = resource ?: return@collect
                    binding.apodSwipeRefreshLayout.isRefreshing = resource is Resource.Loading
                    binding.apodRecyclerView.isVisible = !result.data.isNullOrEmpty()
                    binding.group.isVisible = !binding.apodRecyclerView.isVisible
                    mainAdapter.submitList(result.data)
                }
            }
        }
    }
}