package com.example.afreecatvassignment.data.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.example.afreecatvassignment.data.model.BroadCategoryItem
import com.example.afreecatvassignment.data.model.BroadItem
import com.example.afreecatvassignment.ui.model.BroadCategoryUiModel
import com.example.afreecatvassignment.ui.model.BroadUiModel

fun List<BroadCategoryItem>.toBroadCategoryUiModel(): List<BroadCategoryUiModel> {
    return this.map {
        BroadCategoryUiModel(
            categoryName = it.categoryName,
            categoryNumber = it.categoryNumber
        )
    }
}


fun PagingData<BroadItem>.toBroadUiModel(): PagingData<BroadUiModel> {
    return this.map {
        BroadUiModel(
            broadTitle = it.broadTitle,
            broadNumber = it.broadNumber,
            userId = it.userId,
            userNickName = it.userNickName,
            profileImage = "https:${it.profileImage}",
            broadThumb = "https:${it.broadThumb}",
            totalViewCount = it.totalViewCount
        )
    }
}