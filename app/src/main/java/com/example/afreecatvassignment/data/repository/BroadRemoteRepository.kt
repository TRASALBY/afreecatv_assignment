package com.example.afreecatvassignment.data.repository

import com.example.afreecatvassignment.ui.model.BroadCategoryUiModel
import com.example.afreecatvassignment.ui.model.BroadUiModel


interface BroadRemoteRepository {
    suspend fun getCategoryList() : List<BroadCategoryUiModel>
    suspend fun getBroadList() : List<BroadUiModel>
}