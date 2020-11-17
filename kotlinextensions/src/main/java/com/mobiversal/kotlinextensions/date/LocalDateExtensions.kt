package com.mobiversal.kotlinextensions.date

/**
 * Created by Csaba on 8/28/2019.
 */

import android.content.Context
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*
import com.mobiversal.kotlinextensions.R


fun LocalDateTime.isToday(): Boolean {
    val zonedDate = this.atZone(ZoneId.systemDefault())
    val today = LocalDate.now(ZoneId.systemDefault())
    return zonedDate.toLocalDate() == today
}

fun LocalDateTime.isYesterday(): Boolean {
    val zonedDate = this.atZone(ZoneId.systemDefault())
    val yesterday = LocalDate.now(ZoneId.systemDefault()).minusDays(1)
    return zonedDate.toLocalDate() == yesterday
}

fun LocalDateTime.formatToString(pattern: String): String {
    return this.format(DateTimeFormatter.ofPattern(pattern))
}

fun LocalDateTime.toDateWithDayAndTime(context: Context): String {// TODO with custom pattern and more display possibilities
//    val date = ZonedDateTime.of(this, ZoneId.systemDefault())
    val date = this.appendLocalTimeZoneOffset()

    val timeFormat = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
    val dateFormat = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.getDefault())

    return when {
        this.isToday() -> context.getString(R.string.time_at, context.getString(R.string.time_today), timeFormat.format(date))
        this.isYesterday() -> context.getString(R.string.time_at, context.getString(R.string.time_yesterday), timeFormat.format(date))
        else -> context.getString(R.string.time_at, dateFormat.format(date), timeFormat.format(date))
    }
}

fun LocalDateTime.toDateWithDay(context: Context): String { // TODO with custom pattern
    return when {
        this.isToday() -> context.getString(R.string.time_today)
        this.isYesterday() -> context.getString(R.string.time_yesterday)
        else -> {
//            val date = ZonedDateTime.of(this, ZoneId.systemDefault())
            val date = this.appendLocalTimeZoneOffset()

            val timeFormat = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
            val dateFormat = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.getDefault())
            
            context.getString(R.string.time_at, dateFormat.format(date), timeFormat.format(date))
        }
    }
}

private fun LocalDateTime.appendLocalTimeZoneOffset() : LocalDateTime {
    val date = ZonedDateTime.of(this, ZoneId.systemDefault())
    return this.plusSeconds(date.offset.totalSeconds.toLong())
}