package com.example.afreecatvassignment.ui.model

import androidx.annotation.StringRes
import com.example.afreecatvassignment.R

enum class BroadCategory(@StringRes val stringRes: Int, val number: String) {
    TALK(R.string.talking, "00130000"),
    EAT(R.string.eating_and_cooking, "00330000"),
    MUSIC(R.string.music, "00030000"),
}