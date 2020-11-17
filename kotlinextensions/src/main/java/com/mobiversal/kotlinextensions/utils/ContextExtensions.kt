package com.mobiversal.kotlinextensions.utils

/**
 * Created by Csaba on 8/28/2019.
 */

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat


fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.toast(message: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.isOnline(): Boolean {
    val netInfo =
        (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
    return netInfo != null && netInfo.isConnected
}

fun Context.drawable(@DrawableRes resourceId: Int): Drawable? =
    ContextCompat.getDrawable(this, resourceId)


@ColorInt
fun Context.color(@ColorRes resourceId: Int): Int =
    ContextCompat.getColor(this, resourceId)


fun Context.font(@FontRes fontId: Int): Typeface? =
    ResourcesCompat.getFont(this, fontId)
