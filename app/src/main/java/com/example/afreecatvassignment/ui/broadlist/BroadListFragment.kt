package com.example.afreecatvassignment.ui.broadlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.afreecatvassignment.databinding.FragmentBroadListBinding
import com.example.afreecatvassignment.ui.broadlist.adapter.BroadCategoryAdapter
import com.example.afreecatvassignment.ui.dialog.CategorySelectDialog
import com.example.afreecatvassignment.ui.model.BroadCategoryUiModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BroadListFragment : Fragment(), CategorySelectDialog.CategorySelectCompleteListener {

    private var _binding: FragmentBroadListBinding? = null
    private val binding
        get() = checkNotNull(_binding) { "binding was accessed outside of view lifecycle" }

    private var categorySelectDialog: CategorySelectDialog? = null

    private val broadListViewModel: BroadListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBroadListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSelectedCategories()
        setCategorySelectFab()
    }


    private fun showCategorySelectDialog(selectedCategoryList: List<BroadCategoryUiModel>) {
        categorySelectDialog = CategorySelectDialog(this)
        activity?.supportFragmentManager?.let { fragmentManager ->
            categorySelectDialog?.apply {
                arguments =
                    bundleOf(KEY_SELECTED_CATEGORY to selectedCategoryList.map { it.categoryNumber }
                        .toTypedArray())
                show(
                    fragmentManager,
                    CATEGORY_SELECT_DIALOG
                )
            }
        }
    }

    private fun setCategorySelectFab() {
        binding.fabCategorySelect.setOnClickListener {
            showCategorySelectDialog(broadListViewModel.selectedCategories.value)
        }
    }

    override fun onCategorySelectComplete(categories: List<BroadCategoryUiModel>) {
        broadListViewModel.setSelectedCategories(categories)
    }

    private fun observeSelectedCategories() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                broadListViewModel.selectedCategories.collect {
                    if (it.isEmpty()) {
                        showCategorySelectDialog(it)
                    }
                    setViewPager(it)
                }
            }
        }
    }

    private fun setViewPager(categoryList: List<BroadCategoryUiModel>?) {
        if (categoryList.isNullOrEmpty()) {
            binding.tvPleaseCategorySelect.isVisible = true
        } else {
            binding.tvPleaseCategorySelect.isVisible = false

            val broadCategoryAdapter = BroadCategoryAdapter(this, categoryList)
            binding.viewpagerBroadList.adapter = broadCategoryAdapter

            TabLayoutMediator(
                binding.tabBroadCategory, binding.viewpagerBroadList
            ) { tab, position ->
                tab.text = categoryList[position].categoryName
            }.attach()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val KEY_SELECTED_CATEGORY = "selected_category"
        const val CATEGORY_SELECT_DIALOG = "categorySelectDialog"
    }
}