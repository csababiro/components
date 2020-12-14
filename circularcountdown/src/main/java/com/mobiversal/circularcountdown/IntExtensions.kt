package com.mobiversal.circularcountdown

/**
 * Created by Csaba on 8/28/2019.
 */

import android.content.res.Resources
import android.util.TypedValue

fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.spToPx(): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), Resources.getSystem().displayMetrics).toInt()