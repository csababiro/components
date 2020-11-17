package com.mobiversal.kotlinextensions.view

/**
 * Created by Csaba on 8/28/2019.
 */

import android.os.SystemClock
import android.view.View
import com.mobiversal.kotlinextensions.logs.logDebug
import java.util.*

/** copied from Vibendo project (class made by Csabi)
 *
 * The one and only constructor
 * @param minimumIntervalMsec The minimum allowed time between clicks - any click sooner than this after a previous click will be rejected
 */
abstract class DebounceClickListener(private val minimumInterval: Long = 1000) : View.OnClickListener {

    private val lastClickMap: MutableMap<View, Long>

    // Implement this method in your subclass instead of onClick @param v The view that was clicked
    abstract fun onDebouncedClick(v: View)

    init {
        this.lastClickMap = WeakHashMap<View, Long>()
    }

    override fun onClick(clickedView: View) {
        val previousClickTimestamp = lastClickMap[clickedView]
        val currentTimestamp = SystemClock.uptimeMillis()
        logDebug("onClick previousClickTimestamp: $previousClickTimestamp currentTimestamp: $currentTimestamp")

        lastClickMap[clickedView] = currentTimestamp
        if (previousClickTimestamp == null || Math.abs(currentTimestamp - previousClickTimestamp.toLong()) > minimumInterval) {
            onDebouncedClick(clickedView)
        }
    }
}