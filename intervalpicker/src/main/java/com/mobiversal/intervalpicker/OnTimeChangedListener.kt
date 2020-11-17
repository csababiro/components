package com.mobiversal.intervalpicker

/**
 * Created by Csaba on 9/9/2019.
 */
interface OnTimeChangedListener {

    fun onTimeChanged(view: IntervalPicker, hour: Int, minute: Int, seconds: Int)
}