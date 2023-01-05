package com.example.afreecatvassignment.data.repository

import androidx.paging.PagingData
import com.example.afreecatvassignment.ui.model.BroadCategoryUiModel
import com.example.afreecatvassignment.ui.model.BroadUiModel
import kotlinx.coroutines.flow.Flow


interface BroadRemoteRepository {
    suspend fun getCategoryList(): List<BroadCategoryUiModel>
    suspend fun getBroadList(categoryNumber: String): Flow<PagingData<BroadUiModel>>
}