package com.example.afreecatvassignment.data.datasource

import com.example.afreecatvassignment.data.api.AfreecaApiService
import com.example.afreecatvassignment.data.model.BroadCategoryItem
import javax.inject.Inject

class BroadRemoteDataSourceImpl @Inject constructor(
    private val afreecaApiService: AfreecaApiService
) : BroadRemoteDataSource {
    override suspend fun getCategoryList(): List<BroadCategoryItem> {
        val a = afreecaApiService.getCategoryList()
        return a.broadCategory
    }
}