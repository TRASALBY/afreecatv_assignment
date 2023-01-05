package com.example.afreecatvassignment.ui.broadlist.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.afreecatvassignment.ui.broadlist.CategoryBroadListFragment
import com.example.afreecatvassignment.ui.model.BroadCategory

class BroadCategoryAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = BroadCategory.values().size

    override fun createFragment(position: Int): Fragment = CategoryBroadListFragment()
}