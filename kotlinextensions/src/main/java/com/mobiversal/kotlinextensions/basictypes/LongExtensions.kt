package com.mobiversal.kotlinextensions.basictypes

/**
 * Created by Csaba on 8/28/2019.
 */

import java.util.*


// https://stackoverflow.com/questions/4753251/how-to-go-about-formatting-1200-to-1-2k-in-java
fun Long.formatCount(maxLength: Int = 4): String {
    val suffixes = TreeMap<Long, String>()
    suffixes[1_000L] = "k"
    suffixes[1_000_000L] = "M"
    suffixes[1_000_000_000L] = "G"
    suffixes[1_000_000_000_000L] = "T"
    suffixes[1_000_000_000_000_000L] = "P"
    suffixes[1_000_000_000_000_000_000L] = "E"

    //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
    if (this == Long.MIN_VALUE) return (Long.MIN_VALUE + 1).formatCount()
    if (this < 0) return "-" + (-this).formatCount()
    if (this < 1000) return this.toString() //deal with easy case

    val e = suffixes.floorEntry(this)
    val divideBy = e.key
    val suffix = e.value

    val truncated = this / (divideBy / 10) //the number part of the output times 10
    val hasDecimal = truncated < 100 && truncated / 10.0 != (truncated / 10).toDouble()
    return if (hasDecimal) (truncated / 10.0).toString() + suffix else (truncated / 10).toString() + suffix
}