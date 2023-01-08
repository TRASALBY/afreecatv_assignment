package com.example.afreecatvassignment.ui.broadlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.afreecatvassignment.data.repository.BroadRemoteRepository
import com.example.afreecatvassignment.ui.model.BroadUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryBroadListViewModel @Inject constructor(
    private val broadRemoteRepository: BroadRemoteRepository
) : ViewModel() {

    private val _broadList = MutableStateFlow<PagingData<BroadUiModel>>(PagingData.empty())
    val broadList = _broadList.asStateFlow()

    fun getBroadList(categoryNumber: String) {
        viewModelScope.launch {
            broadRemoteRepository.getBroadList(categoryNumber)
                .cachedIn(viewModelScope)
                .collectLatest {
                    _broadList.value = it
                }
        }
    }
}