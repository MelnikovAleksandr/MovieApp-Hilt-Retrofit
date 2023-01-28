package ru.asmelnikov.android.movieapphiltretrofit.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import ru.asmelnikov.android.movieapphiltretrofit.R

fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.poster_placeholder)
        .error(R.drawable.ic_image_error)
        .into(this)
}

