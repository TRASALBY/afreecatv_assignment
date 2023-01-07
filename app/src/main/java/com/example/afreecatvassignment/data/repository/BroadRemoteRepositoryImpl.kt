package com.example.afreecatvassignment.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.afreecatvassignment.data.api.AfreecaApiService
import com.example.afreecatvassignment.data.datasource.BroadRemoteDataSource
import com.example.afreecatvassignment.data.datasource.BroadRemotePagingSource
import com.example.afreecatvassignment.data.mapper.toBroadCategoryUiModel
import com.example.afreecatvassignment.data.mapper.toBroadUiModel
import com.example.afreecatvassignment.ui.model.BroadCategoryUiModel
import com.example.afreecatvassignment.ui.model.BroadUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BroadRemoteRepositoryImpl @Inject constructor(
    private val apiService: AfreecaApiService,
    private val broadRemoteDataSource: BroadRemoteDataSource
) : BroadRemoteRepository {
    override suspend fun getCategoryList(): Flow<List<BroadCategoryUiModel>> {
        return flow {
            runCatching {
                broadRemoteDataSource.getCategoryList()
            }.onSuccess {
                emit(it.toBroadCategoryUiModel())
            }.onFailure { throwable ->
                throw throwable
            }
        }
    }

    override fun getBroadList(categoryNumber: String): Flow<PagingData<BroadUiModel>> =
        Pager(
            PagingConfig(
                pageSize = PAGE_SIZE
            )
        ) {
            BroadRemotePagingSource(apiService, categoryNumber)
        }.flow.map {
            it.toBroadUiModel()
        }

    companion object {
        private const val PAGE_SIZE = 20
    }
}