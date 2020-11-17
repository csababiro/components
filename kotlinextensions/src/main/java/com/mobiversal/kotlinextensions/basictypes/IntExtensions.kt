package com.mobiversal.kotlinextensions.basictypes

/**
 * Created by Csaba on 8/28/2019.
 */

import android.content.res.Resources

fun Int.pxToDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.toBoolean(): Boolean = this == 1

fun Int.isEven(): Boolean = this % 2 == 0