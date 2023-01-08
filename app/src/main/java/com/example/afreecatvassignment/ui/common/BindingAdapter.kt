package com.example.afreecatvassignment.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("bind_image_url")
fun setImageFromUrl(imageView: ImageView, url: String?) {
    if (url.isNullOrEmpty()) return

    Glide.with(imageView)
        .load(url)
        .into(imageView)
}