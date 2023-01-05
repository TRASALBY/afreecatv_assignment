package com.example.afreecatvassignment.ui.broadlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.afreecatvassignment.data.repository.BroadRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryBroadListViewModel @Inject constructor(
    private val broadRemoteRepository: BroadRemoteRepository
) : ViewModel(){
    init {
        viewModelScope.launch {
            val a = broadRemoteRepository.getCategoryList()
            val b = broadRemoteRepository.getBroadList()
            println()
        }
    }
}