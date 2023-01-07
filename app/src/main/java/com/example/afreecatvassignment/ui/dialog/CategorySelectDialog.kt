package com.example.afreecatvassignment.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.afreecatvassignment.R
import com.example.afreecatvassignment.databinding.DialogCategorySelectBinding
import com.example.afreecatvassignment.ui.broadlist.BroadListFragment.Companion.KEY_SELECTED_CATEGORY
import com.example.afreecatvassignment.ui.model.BroadCategoryUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategorySelectDialog(
    private val categorySelectCompleteListener: CategorySelectCompleteListener
) : DialogFragment(), CategorySelectAdapter.CategoryChangeListener {

    private var _binding: DialogCategorySelectBinding? = null
    private val binding
        get() = checkNotNull(_binding) { "binding was accessed outside of view lifecycle" }

    private val viewModel: CategorySelectViewModel by viewModels()

    private lateinit var categorySelectAdapter: CategorySelectAdapter

    interface CategorySelectCompleteListener {
        fun onCategorySelectComplete(categories: List<BroadCategoryUiModel>)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.categorySelect)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogCategorySelectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSelectedCategory()
        setRecyclerView()
        setCompletedBtn()
        observeCategories()
    }

    private fun initSelectedCategory() {
        val selectedCategoryNumbers = arguments?.getStringArray(KEY_SELECTED_CATEGORY) ?: return
        viewModel.setSelectedCategories(selectedCategoryNumbers)
    }

    private fun setCompletedBtn() {
        binding.btnSelectCategory.setOnClickListener {
            categorySelectCompleteListener.onCategorySelectComplete(viewModel.selectedCategories.value)
            dismiss()
        }
    }

    private fun observeCategories() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categories.collect {
                    categorySelectAdapter.submitList(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedCategories.collect {
                    binding.btnSelectCategory.isEnabled = it.size >= MAX_SELECT_COUNT
                }
            }
        }
    }

    private fun setRecyclerView() {
        categorySelectAdapter = CategorySelectAdapter(this)
        binding.rvCategoryList.adapter = categorySelectAdapter
    }

    override fun onCategoryChangeListener(category: BroadCategoryUiModel, checked: Boolean) {
        viewModel.changeCategorySelected(category, checked)
    }


    companion object {
        const val MAX_SELECT_COUNT = 3
    }
}