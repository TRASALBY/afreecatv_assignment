package com.example.afreecatvassignment.di

import com.example.afreecatvassignment.data.api.AfreecaApiService
import com.example.afreecatvassignment.data.datasource.BroadRemoteDataSource
import com.example.afreecatvassignment.data.datasource.BroadRemoteDataSourceImpl
import com.example.afreecatvassignment.data.repository.BroadRemoteRepository
import com.example.afreecatvassignment.data.repository.BroadRemoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideBroadRemoteRepository(
        broadRemoteDataSource: BroadRemoteDataSource
    ): BroadRemoteRepository {
        return BroadRemoteRepositoryImpl(broadRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideBroadRemoteDataSource(
        api: AfreecaApiService
    ): BroadRemoteDataSource {
        return BroadRemoteDataSourceImpl(api)
    }
}