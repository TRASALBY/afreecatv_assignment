package com.example.afreecatvassignment.ui.broadlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.afreecatvassignment.databinding.FragmentBroadListBinding
import com.example.afreecatvassignment.ui.broadlist.adapter.BroadCategoryAdapter
import com.example.afreecatvassignment.ui.model.BroadCategory
import com.google.android.material.tabs.TabLayoutMediator

class BroadListFragment : Fragment() {

    private var _binding: FragmentBroadListBinding? = null
    private val binding
        get() = checkNotNull(_binding) { "binding was accessed outside of view lifecycle" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBroadListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val broadCategoryAdapter = BroadCategoryAdapter(this)
        binding.viewpagerBroadList.adapter = broadCategoryAdapter

        TabLayoutMediator(binding.tabBroadCategory, binding.viewpagerBroadList) { tab, position ->
            tab.text = getString(BroadCategory.values()[position].stringRes)
        }.attach()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}