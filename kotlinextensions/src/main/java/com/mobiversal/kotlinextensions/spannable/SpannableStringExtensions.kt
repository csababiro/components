package com.mobiversal.kotlinextensions.spannable

/**
 * Created by Csaba on 8/28/2019.
 */

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.annotation.ColorInt
import com.mobiversal.kotlinextensions.spannable.CustomTypefaceSpan


fun SpannableString.withColorSpan(spanPart: String, @ColorInt color: Int): SpannableString {
    val spanStartIndex = indexOf(spanPart)
    setSpan(
        ForegroundColorSpan(color),
        spanStartIndex,
        spanStartIndex + spanPart.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return this
}

// TODO get typeface method
fun SpannableString.withTypefaceSpan(clickablePart: String, typeface: Typeface?): SpannableString {
    typeface?.let {
        val spanStartIndex = indexOf(clickablePart)
        setSpan(
            CustomTypefaceSpan("", it),
            spanStartIndex,
            spanStartIndex + clickablePart.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return this
}

/**
 * Helps to set clickable part in text.
 *
 * Don't forget to set android:textColorLink="@color/link" (click selector) and
 * android:textColorHighlight="@color/window_background" (background color while clicks)
 * in the TextView where you will use this.
 */
fun SpannableString.withClickableSpan(spanPart: String, shouldUnderlineText: Boolean, onClickListener: () -> Unit): SpannableString {
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View?) = onClickListener.invoke()

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = shouldUnderlineText
        }
    }
    val spanStartIndex = indexOf(spanPart)
    setSpan(
        clickableSpan,
        spanStartIndex,
        spanStartIndex + spanPart.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return this
}