package com.mobiversal.kotlinextensions.basictypes

/**
 * Created by Csaba on 8/28/2019.
 */

import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.Patterns
import androidx.annotation.ColorInt
import java.text.ParseException
import java.text.SimpleDateFormat


fun String.isEmailValid(): Boolean {
    return !this.isBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}