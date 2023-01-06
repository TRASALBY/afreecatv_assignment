package com.example.afreecatvassignment.ui.broadlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.afreecatvassignment.databinding.FragmentBroadListBinding
import com.example.afreecatvassignment.ui.broadlist.adapter.BroadCategoryAdapter
import com.example.afreecatvassignment.ui.dialog.CategorySelectDialog
import com.example.afreecatvassignment.ui.model.BroadCategoryUiModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BroadListFragment : Fragment(), CategorySelectDialog.CategorySelectCompleteListener {

    private var _binding: FragmentBroadListBinding? = null
    private val binding
        get() = checkNotNull(_binding) { "binding was accessed outside of view lifecycle" }

    private var categorySelectDialog: CategorySelectDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBroadListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showCategorySelectDialog()
        setCategorySelectFab()
    }


    private fun showCategorySelectDialog() {
        categorySelectDialog = CategorySelectDialog(this)
        activity?.supportFragmentManager?.let { fragmentManager ->
            categorySelectDialog?.show(
                fragmentManager,
                "categorySelectDialog"
            )
        }
    }

    private fun setCategorySelectFab() {
        binding.fabCategorySelect.setOnClickListener {
            showCategorySelectDialog()
        }
    }

    override fun onCategorySelectComplete(categories: List<BroadCategoryUiModel>) {
        setViewPager(categories)
    }

    private fun setViewPager(categoryList: List<BroadCategoryUiModel>) {
        val broadCategoryAdapter = BroadCategoryAdapter(this)
        binding.viewpagerBroadList.adapter = broadCategoryAdapter

        TabLayoutMediator(binding.tabBroadCategory, binding.viewpagerBroadList) { tab, position ->
            tab.text = categoryList[position].categoryName
        }.attach()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}