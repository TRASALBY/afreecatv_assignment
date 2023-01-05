package com.example.afreecatvassignment.data.repository

import com.example.afreecatvassignment.data.datasource.BroadRemoteDataSource
import com.example.afreecatvassignment.data.mapper.toBroadCategoryUiModel
import com.example.afreecatvassignment.data.mapper.toBroadUiModel
import com.example.afreecatvassignment.ui.model.BroadCategoryUiModel
import com.example.afreecatvassignment.ui.model.BroadUiModel
import javax.inject.Inject

class BroadRemoteRepositoryImpl @Inject constructor(
    private val broadRemoteDataSource: BroadRemoteDataSource
) : BroadRemoteRepository {
    override suspend fun getCategoryList(): List<BroadCategoryUiModel> {
        return broadRemoteDataSource.getCategoryList().toBroadCategoryUiModel()
    }

    override suspend fun getBroadList(): List<BroadUiModel> {
        return broadRemoteDataSource.getBroadList().toBroadUiModel()
    }
}