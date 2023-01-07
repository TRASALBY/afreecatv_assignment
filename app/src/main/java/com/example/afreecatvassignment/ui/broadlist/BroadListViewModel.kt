package com.example.afreecatvassignment.ui.broadlist

import androidx.lifecycle.ViewModel
import com.example.afreecatvassignment.ui.model.BroadCategoryUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BroadListViewModel : ViewModel() {
    private val _selectedCategories = MutableStateFlow<List<BroadCategoryUiModel>>(emptyList())
    val selectedCategories = _selectedCategories.asStateFlow()

    fun setSelectedCategories(categories: List<BroadCategoryUiModel>) {
        _selectedCategories.value = categories
    }
}