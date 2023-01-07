package com.example.afreecatvassignment.ui.broadlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.example.afreecatvassignment.databinding.FragmentCategoryBroadListBinding
import com.example.afreecatvassignment.ui.broadlist.adapter.BroadCategoryAdapter
import com.example.afreecatvassignment.ui.broadlist.adapter.BroadPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryBroadListFragment : Fragment() {

    private var _binding: FragmentCategoryBroadListBinding? = null
    private val binding
        get() = checkNotNull(_binding) { "binding was accessed outside of view lifecycle" }

    private lateinit var broadAdapter: BroadPagingAdapter
    private lateinit var categoryNumber: String

    private val viewModel: CategoryBroadListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBroadListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCategoryNumber()
        setRecyclerView()
        setSwipeRefresh()
        collectBroadList()
    }

    private fun setCategoryNumber() {
        categoryNumber = arguments?.getString(BroadCategoryAdapter.KEY_CATEGORY) ?: ""
    }

    private fun setSwipeRefresh() {
        binding.srlCategoryBroad.setOnRefreshListener {
            broadAdapter.refresh()
            binding.srlCategoryBroad.isRefreshing = false
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                broadAdapter.loadStateFlow.collect {
                    binding.piCategoryBroad.isVisible = it.source.append is LoadState.Loading
                }
            }
        }
    }

    private fun setRecyclerView() {
        broadAdapter = BroadPagingAdapter()
        binding.rvBroadList.adapter = broadAdapter
    }

    private fun collectBroadList() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getBroadList(categoryNumber).collectLatest { pagingData ->
                    broadAdapter.submitData(pagingData)
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}