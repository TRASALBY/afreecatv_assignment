package com.example.afreecatvassignment.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BroadUiModel(
    val broadTitle: String,
    val broadNumber: String,
    val userId: String,
    val userNickName: String,
    val profileImage: String,
    val broadThumb: String,
    val totalViewCount: Int
): Parcelable
