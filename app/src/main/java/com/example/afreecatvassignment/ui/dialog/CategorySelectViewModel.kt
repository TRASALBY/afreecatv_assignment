package com.example.afreecatvassignment.ui.dialog

import android.util.Log
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

    fun setSelectedCategories(selectedCategoryNumbers: Array<String>) {
        viewModelScope.launch {
            _categories.value = broadRemoteRepository.getCategoryList().map {
                if (selectedCategoryNumbers.contains(it.categoryNumber)) {
                    val newBroadCategoryUiModel = it.copy(isSelected = true)
                    _selectedCategories.value += newBroadCategoryUiModel
                    newBroadCategoryUiModel
                } else {
                    it
                }
            }
        }
    }

    fun changeCategorySelected(categoryUiModel: BroadCategoryUiModel, checked: Boolean) {
        val nowCategory =
            _selectedCategories.value.firstOrNull() { it.categoryNumber == categoryUiModel.categoryNumber }
        val isCategorySelected = nowCategory != null

        if (checked && isCategorySelected.not()) {
            _selectedCategories.value = _selectedCategories.value + categoryUiModel
        } else if (checked.not() && isCategorySelected) {
            nowCategory ?: return
            _selectedCategories.value = _selectedCategories.value - nowCategory
        }

        _categories.value = _categories.value.map {
            if (it.categoryNumber == categoryUiModel.categoryNumber) {
                it.copy(isSelected = it.isSelected.not())
            } else {
                it
            }
        }
        Log.d(
            "selectedCategory111",
            "${_selectedCategories.value.map { "${it.categoryName},${it.categoryNumber}" }}"
        )
    }
}