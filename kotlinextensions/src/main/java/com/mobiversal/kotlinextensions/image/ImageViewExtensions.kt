package com.mobiversal.kotlinextensions.image

/**
 * Created by Csaba on 8/28/2019.
 */

import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.mobiversal.kotlinextensions.R


fun ImageView.loadImage(
    url: String?,
    @DrawableRes placeholderRes: Int = R.drawable.shape_image_placeholder,
    @DrawableRes errorRes: Int = R.drawable.shape_image_placeholder
) {
    val requestOptions: RequestOptions = RequestOptions().apply {
        diskCacheStrategy(DiskCacheStrategy.ALL)
        placeholder(placeholderRes)
        error(errorRes)
    }

    Glide.with(context)
        .asBitmap()
        .load(url)
        .apply(requestOptions)
        .transition(withCrossFade())
        .into(this)
}

fun ImageView.loadImage(
    uri: Uri?,
    @DrawableRes placeholderRes: Int = R.drawable.shape_image_placeholder,
    @DrawableRes errorRes: Int = R.drawable.shape_image_placeholder
) {
    val requestOptions: RequestOptions = RequestOptions().apply {
        diskCacheStrategy(DiskCacheStrategy.ALL)
        placeholder(placeholderRes)
        error(errorRes)
    }

    Glide.with(context)
        .asBitmap()
        .load(uri)
        .apply(requestOptions)
        .transition(withCrossFade())
        .into(this)
}

fun ImageView.loadBlurImage(
    url: String?,
    @DrawableRes placeholderRes: Int = R.drawable.shape_image_placeholder,
    @DrawableRes errorRes: Int = R.drawable.shape_image_placeholder
) {
    val requestOptions: RequestOptions = RequestOptions().apply {
        diskCacheStrategy(DiskCacheStrategy.ALL)
        placeholder(placeholderRes)
        override(100, 100) // https://stackoverflow.com/questions/37944879/android-glide-show-a-blurred-image-before-loading-actual-image
        error(errorRes)
    }

    Glide.with(context)
        .asBitmap()
        .load(url)
        .apply(requestOptions)
        .transition(withCrossFade())
        .into(this)
}

