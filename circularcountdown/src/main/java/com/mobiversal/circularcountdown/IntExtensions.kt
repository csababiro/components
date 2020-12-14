package com.mobiversal.circularcountdown

/**
 * Created by Csaba on 8/28/2019.
 */

import android.content.res.Resources

fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()