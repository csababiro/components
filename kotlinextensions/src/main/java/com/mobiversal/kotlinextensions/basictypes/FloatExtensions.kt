package com.mobiversal.kotlinextensions.basictypes

/**
 * Created by Csaba on 8/28/2019.
 */

import android.content.res.Resources
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun Float.pxToDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

fun Float.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Float.roundTwoDecimals() = round(2)

fun Float.roundOneDecimal() = round(1)

private fun Float.round(decimalPlaces: Int): String = DecimalFormat("#.##").apply {
            roundingMode = RoundingMode.CEILING
        }.format(this)

fun Float.formatWithLocale(): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault())
    numberFormat.isGroupingUsed = false
    numberFormat.maximumFractionDigits = 2
    numberFormat.minimumFractionDigits = 0
    numberFormat.minimumIntegerDigits = 1
    return numberFormat.format(this)
}