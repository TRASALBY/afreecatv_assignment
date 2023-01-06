package com.example.afreecatvassignment.ui.broadlist

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
class BroadListViewModel @Inject constructor(
    private val broadRemoteRepository: BroadRemoteRepository
) : ViewModel() {
    private val _categoryList = MutableStateFlow<List<BroadCategoryUiModel>?>(null)
    val categoryList = _categoryList.asStateFlow()

    init {
        viewModelScope.launch {
            _categoryList.value = broadRemoteRepository.getCategoryList()
        }
    }
}