package com.mustafafidan.itunessearch.common

import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

@BindingAdapter("adapter")
fun RecyclerView.setAdapter(adapter : RecyclerView.Adapter<*>?) {
    adapter.let { this.adapter = adapter }
}

@BindingAdapter("android:src")
fun setImageUrl(imageView: ImageView, url: String?) {
    if (!TextUtils.isEmpty(url)) {
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    } else {
        imageView.setImageDrawable(null)
    }
}