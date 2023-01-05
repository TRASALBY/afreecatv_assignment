package com.example.afreecatvassignment.ui

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("bind_image_url")
fun setImageFromUrl(imageView: ImageView, url: String?) {
    if (url.isNullOrEmpty()) return

    Log.d("imageUrl",url)

    Glide.with(imageView)
        .load("https:$url")
        .into(imageView)
}