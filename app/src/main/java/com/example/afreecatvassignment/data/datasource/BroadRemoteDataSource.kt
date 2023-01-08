package com.example.afreecatvassignment.data.datasource

import com.example.afreecatvassignment.data.model.BroadCategoryItem
import kotlinx.coroutines.flow.Flow

interface BroadRemoteDataSource {
    fun getCategoryList(): Flow<List<BroadCategoryItem>>
}