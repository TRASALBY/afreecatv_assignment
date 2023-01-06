package com.example.afreecatvassignment.data.datasource

import com.example.afreecatvassignment.data.model.BroadCategoryItem

interface BroadRemoteDataSource {
    suspend fun getCategoryList(): List<BroadCategoryItem>
}