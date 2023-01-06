package com.example.afreecatvassignment.ui.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.afreecatvassignment.data.repository.BroadRemoteRepository
import com.example.afreecatvassignment.ui.model.BroadCategoryUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategorySelectViewModel @Inject constructor(
    private val broadRemoteRepository: BroadRemoteRepository
) : ViewModel() {
    private val _categories = MutableStateFlow<List<BroadCategoryUiModel>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _selectedCategories = MutableStateFlow<List<BroadCategoryUiModel>>(emptyList())
    val selectedCategories = _selectedCategories.asStateFlow()

    init {
        viewModelScope.launch {
            _categories.value = broadRemoteRepository.getCategoryList()
        }
    }

    companion object {
        const val MAX_SELECT_COUNT = 3
    }
}