package com.example.afreecatvassignment.data.repository

import androidx.paging.PagingData
import com.example.afreecatvassignment.ui.model.BroadCategoryUiModel
import com.example.afreecatvassignment.ui.model.BroadUiModel
import kotlinx.coroutines.flow.Flow


interface BroadRemoteRepository {
    fun getCategoryList(): Flow<List<BroadCategoryUiModel>>
    fun getBroadList(categoryNumber: String): Flow<PagingData<BroadUiModel>>
}