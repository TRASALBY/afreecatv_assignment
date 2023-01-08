package com.example.afreecatvassignment.ui.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.afreecatvassignment.data.repository.BroadRemoteRepository
import com.example.afreecatvassignment.ui.common.UiState
import com.example.afreecatvassignment.ui.model.BroadCategoryUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class CategorySelectViewModel @Inject constructor(
    private val broadRemoteRepository: BroadRemoteRepository
) : ViewModel() {
    private val _categories = MutableStateFlow<UiState<List<BroadCategoryUiModel>>>(UiState.Loading)
    val categories = _categories.asStateFlow()

    private val _selectedCategories = MutableStateFlow<List<BroadCategoryUiModel>>(emptyList())
    val selectedCategories = _selectedCategories.asStateFlow()

    fun setSelectedCategories(selectedCategoryNumbers: Array<String>) {
        viewModelScope.launch {
            broadRemoteRepository.getCategoryList().catch { exception ->
                _categories.update {
                    when (exception) {
                        is UnknownHostException -> UiState.NetworkFailure
                        else -> UiState.Failure
                    }
                }
            }.collect { categoryList ->
                _categories.update {
                    UiState.Success(
                        categoryList.map {
                            if (selectedCategoryNumbers.contains(it.categoryNumber)) {
                                val newBroadCategoryUiModel = it.copy(isSelected = true)
                                _selectedCategories.value += newBroadCategoryUiModel
                                newBroadCategoryUiModel
                            } else {
                                it
                            }
                        }
                    )
                }
            }
        }
    }

    fun changeCategorySelected(categoryUiModel: BroadCategoryUiModel, checked: Boolean) {
        val nowCategory =
            _selectedCategories.value.firstOrNull { it.categoryNumber == categoryUiModel.categoryNumber }
        val isCategorySelected = nowCategory != null

        if (checked && isCategorySelected.not()) {
            _selectedCategories.value = _selectedCategories.value + categoryUiModel
        } else if (checked.not() && isCategorySelected) {
            nowCategory ?: return
            _selectedCategories.value = _selectedCategories.value - nowCategory
        }

        if (_categories.value is UiState.Success) {
            _categories.update {
                UiState.Success(
                    (_categories.value as UiState.Success<List<BroadCategoryUiModel>>).item.map {
                        if (it.categoryNumber == categoryUiModel.categoryNumber) {
                            it.copy(isSelected = it.isSelected.not())
                        } else {
                            it
                        }
                    }
                )
            }
        }
    }
}