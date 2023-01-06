package com.example.afreecatvassignment.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.afreecatvassignment.data.api.AfreecaApiService
import com.example.afreecatvassignment.data.model.BroadItem
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BroadRemotePagingSource @Inject constructor(
    private val afreecaApiService: AfreecaApiService,
    private val categoryNumber: String
) : PagingSource<Int, BroadItem>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BroadItem> {
        val start = params.key ?: STARTING_KEY
        return try {
            val response = afreecaApiService.getBroadList(
                selectValue = categoryNumber,
                pageNumber = start
            )
            if (start != STARTING_KEY) delay(LOAD_DELAY_MILLIS)
            LoadResult.Page(
                data = response.broadItemList,
                prevKey = if (start == STARTING_KEY) null else start - 1,
                nextKey = if (response.page * 20 > response.totalCount) {
                    null
                } else {
                    start + 1
                }
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, BroadItem>): Int? = null

    companion object {
        private const val STARTING_KEY = 1
        private const val LOAD_DELAY_MILLIS = 3000L
    }

}