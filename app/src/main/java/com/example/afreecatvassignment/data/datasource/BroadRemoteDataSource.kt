package com.example.afreecatvassignment.data.datasource

import com.example.afreecatvassignment.data.model.BroadCategoryItem
import com.example.afreecatvassignment.data.model.BroadItem

interface BroadRemoteDataSource {
    suspend fun getCategoryList() : List<BroadCategoryItem>
    suspend fun getBroadList() : List<BroadItem>
}