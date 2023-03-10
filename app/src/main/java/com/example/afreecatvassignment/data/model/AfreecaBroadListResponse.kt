package com.example.afreecatvassignment.data.model

import com.google.gson.annotations.SerializedName

data class AfreecaBroadListResponse(
    @SerializedName("total_cnt")
    val totalCount: Int,
    @SerializedName("page_no")
    val page: Int,
    @SerializedName("broad")
    val broadItemList: List<BroadItem>
)

data class BroadItem(
    @SerializedName("broad_title")
    val broadTitle: String,
    @SerializedName("broad_no")
    val broadNumber: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("user_nick")
    val userNickName: String,
    @SerializedName("profile_img")
    val profileImage: String,
    @SerializedName("broad_thumb")
    val broadThumb: String,
    @SerializedName("total_view_cnt")
    val totalViewCount: Int
)