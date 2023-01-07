package com.example.afreecatvassignment.ui.broadlist.adapter

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.afreecatvassignment.ui.broadlist.CategoryBroadListFragment
import com.example.afreecatvassignment.ui.model.BroadCategoryUiModel

class BroadCategoryAdapter(
    fragment: Fragment,
    private val categoryList: List<BroadCategoryUiModel>
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = categoryList.size

    override fun createFragment(position: Int): Fragment {
        val fragment = CategoryBroadListFragment()
        fragment.arguments = bundleOf(KEY_CATEGORY to categoryList[position].categoryNumber)
        return fragment
    }

    companion object {
        const val KEY_CATEGORY = "category"
    }
}