package com.example.afreecatvassignment.ui.broadlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.afreecatvassignment.data.repository.BroadRemoteRepository
import com.example.afreecatvassignment.ui.model.BroadUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CategoryBroadListViewModel @Inject constructor(
    private val broadRemoteRepository: BroadRemoteRepository
) : ViewModel() {

    fun getBroadList(categoryNumber: String): Flow<PagingData<BroadUiModel>> {
        return broadRemoteRepository.getBroadList(categoryNumber).cachedIn(viewModelScope)
    }
}