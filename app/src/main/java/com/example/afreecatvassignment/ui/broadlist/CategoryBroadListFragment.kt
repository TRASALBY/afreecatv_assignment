package com.example.afreecatvassignment.ui.broadlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.example.afreecatvassignment.R
import com.example.afreecatvassignment.databinding.FragmentCategoryBroadListBinding
import com.example.afreecatvassignment.ui.broadlist.adapter.BroadCategoryAdapter
import com.example.afreecatvassignment.ui.broadlist.adapter.BroadPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException

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
                    val append = it.source.append
                    val refresh = it.source.refresh
                    it.refresh
                    when (refresh) {
                        is LoadState.Error -> {
                            when (refresh.error) {
                                is UnknownHostException -> {
                                    Toast.makeText(
                                        requireContext(),
                                        R.string.error_network_message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    showRecyclerView(false, R.string.please_check_network)
                                }
                                is HttpException -> {
                                    Toast.makeText(
                                        requireContext(),
                                        R.string.error_fetching_message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    showRecyclerView(false, R.string.please_check_network)
                                }
                            }
                        }
                        else ->
                            if (refresh is LoadState.NotLoading && broadAdapter.itemCount == 0) {
                                showRecyclerView(false, R.string.empty_broad_list)
                            } else {
                                showRecyclerView(true)
                            }

                    }
                    binding.piCategoryBroad.isVisible = append is LoadState.Loading
                }
            }
        }
    }

    private fun setRecyclerView() {
        broadAdapter = BroadPagingAdapter()
        binding.rvBroadList.adapter = broadAdapter
    }

    private fun collectBroadList() {
        viewModel.getBroadList(categoryNumber)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.broadList.collectLatest { pagingData ->
                    broadAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun showRecyclerView(isVisible: Boolean, @StringRes errorMessage: Int? = null) {
        binding.rvBroadList.isVisible = isVisible
        binding.tvBroadListMessage.isVisible = isVisible.not()
        if (isVisible.not()) {
            binding.tvBroadListMessage.text = errorMessage?.let { getString(it) }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}