package com.example.afreecatvassignment.data.api

import com.example.afreecatvassignment.data.model.AfreecaBroadListResponse
import com.example.afreecatvassignment.data.model.AfreecaCategoryListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AfreecaApiService {

    @GET("broad/category/list")
    suspend fun getCategoryList(
        @Query("client_id") clientId: String = CLIENT_ID,
        @Query("locale") locale: String = "ko_KR"
    ): AfreecaCategoryListResponse

    @GET("broad/list")
    suspend fun getBroadList(
        @Query("client_id") clientId: String = CLIENT_ID,
        @Query("select_value") selectValue: String
    ): AfreecaBroadListResponse

    companion object {
        const val CLIENT_ID = "af_mobilelab_dev_e0f147f6c034776add2142b425e81777"
    }
}