package com.example.afreecatvassignment.data.model

import com.google.gson.annotations.SerializedName

data class AfreecaCategoryListResponse(
    @SerializedName("broad_category")
    val broadCategory: List<BroadCategoryItem>
)

data class BroadCategoryItem(
    @SerializedName("cate_name")
    val categoryName: String,
    @SerializedName("cate_no")
    val categoryNumber: Int
)
