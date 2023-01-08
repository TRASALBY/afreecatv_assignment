package com.example.afreecatvassignment.data.datasource

import com.example.afreecatvassignment.data.api.AfreecaApiService
import com.example.afreecatvassignment.data.model.BroadCategoryItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BroadRemoteDataSourceImpl @Inject constructor(
    private val afreecaApiService: AfreecaApiService
) : BroadRemoteDataSource {
    override fun getCategoryList(): Flow<List<BroadCategoryItem>> {
        return flow {
            runCatching {
                afreecaApiService.getCategoryList()
            }.onSuccess {
                emit(it.broadCategory)
            }.onFailure { exception ->
                throw exception
            }
        }
    }
}