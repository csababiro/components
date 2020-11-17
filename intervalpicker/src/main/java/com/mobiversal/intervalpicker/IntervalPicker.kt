package com.mobiversal.intervalpicker

import android.content.Context
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.NumberPicker
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.mobiversal.intervalpicker.ConstantsTimeIntervalPicker.*
import com.mobiversal.intervalpicker.UtilsTimeIntervalPicker.TWO_DIGIT_FORMATTER
import com.mobiversal.intervalpicker.magic.CustomTimePicker

/**
 * Created by Csaba on 9/6/2019.
 */

open class IntervalPicker @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : FrameLayout(context, attrs, defStyle) {

    var textSizeResource = 0f
    var textColorResource = 0
    var errorTextColorResource = 0
    var textColor = 0
    var errorTextColor = 0
    var fontResource = 0


    private var mCurrentHour = 0 // 0-n
    private var mCurrentMinute = 0 // 0-59
    private var mCurrentSeconds = 0 // 0-59

    private var totalDurationInSeconds: Int = 0

    private var totalMaxHours: Int = 0
    private var totalMaxMinutes: Int = 0
    private var totalMaxSeconds: Int = 0

    private var minHours: Int = 0
    private var minMinutes: Int = 0
    private var minSeconds: Int = 0

    // ui components
    private val mHourPicker: CustomNumberPicker
    private val mMinutePicker: CustomNumberPicker
    private val mSecondPicker: CustomNumberPicker
    private val mHourSeparator: TextView
    private val mMinuteSeparator: TextView

    // callbacks
    private var mOnTimeChangedListener: OnTimeChangedListener? = null

    var currentHours: Int
        get() = mCurrentHour
        set(currentHour) {
            this.mCurrentHour = currentHour
            updateHourDisplay()
        }

    var currentMinutes: Int
        get() = mCurrentMinute
        set(currentMinute) {
            this.mCurrentMinute = currentMinute
            updateMinuteDisplay()
        }

    var currentSeconds: Int
        get() = mCurrentSeconds
        set(currentSeconds) {
            this.mCurrentSeconds = currentSeconds
            updateSecondsDisplay()
        }


    var currentValueInSeconds: Int
        get() = mCurrentHour * 3600 + mCurrentMinute * 60 + mCurrentSeconds
        set(durationInSeconds) {

            mCurrentHour = durationInSeconds / 3600
            mCurrentMinute = durationInSeconds % 3600 / 60
            mCurrentSeconds = durationInSeconds % 60

            mHourPicker.value = mCurrentHour
            mMinutePicker.value = mCurrentMinute
            mSecondPicker.value = mCurrentSeconds
        }

    init {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.interval_picker, this, true)

        // hour
        mHourPicker = findViewById<View>(R.id.hour) as CustomNumberPicker
        mHourSeparator = findViewById<View>(R.id.hour_separator) as TextView
        mHourPicker.minValue = DEFAULT_HOURS_MIN
        mHourPicker.maxValue = DEFAULT_HOURS_MAX
        mHourPicker.setFormatter(TWO_DIGIT_FORMATTER)
        mHourPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            mCurrentHour = newVal
            onTimeChanged()
        }
        applyTextProperties(mHourPicker)

        // digits of minute
        mMinutePicker = findViewById<View>(R.id.minute) as CustomNumberPicker
        mMinuteSeparator = findViewById<View>(R.id.minute_separator) as TextView
        mMinutePicker.minValue = DEFAULT_MINUTES_MIN
        mMinutePicker.maxValue = DEFAULT_MINUTES_MAX
        mMinutePicker.setFormatter(TWO_DIGIT_FORMATTER)
        mMinutePicker.setOnValueChangedListener { spinner, oldVal, newVal ->
            mCurrentMinute = newVal
            onTimeChanged()
        }
        applyTextProperties(mMinutePicker)

        // digits of seconds
        mSecondPicker = findViewById<View>(R.id.seconds) as CustomNumberPicker
        mSecondPicker.minValue = DEFAULT_SECONDS_MIN
        mSecondPicker.maxValue = DEFAULT_SECONDS_MAX
        mSecondPicker.setFormatter(TWO_DIGIT_FORMATTER)
        mSecondPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            mCurrentSeconds = newVal
            onTimeChanged()
        }
        applyTextProperties(mHourPicker)

        if (!isEnabled) {
            isEnabled = false
        }
    }

    private fun applyTextProperties(numberPicker: CustomNumberPicker) {
        numberPicker.textColor = textColor
        numberPicker.textColorResource = textColorResource
        numberPicker.errorTextColor = errorTextColor
        numberPicker.errorTextColorResource = errorTextColorResource
        numberPicker.textSizeResource = textSizeResource
        numberPicker.fontResource = fontResource
        numberPicker.updateView()
    }


    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        mMinutePicker.isEnabled = enabled
        mHourPicker.isEnabled = enabled
    }

    /**
     * Used to save / restore state of time picker
     */
    internal class SavedState : View.BaseSavedState {

        val hour: Int
        val minute: Int

        internal constructor(superState: Parcelable, hour: Int, minute: Int) : super(superState) {
            this.hour = hour
            this.minute = minute
        }

        internal constructor(parcel: Parcel) : super(parcel) {
            hour = parcel.readInt()
            minute = parcel.readInt()
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            super.writeToParcel(dest, flags)
            dest.writeInt(hour)
            dest.writeInt(minute)
        }

        companion object {

            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(parcel: Parcel): SavedState {
                    return SavedState(parcel)
                }

                override fun newArray(size: Int): Array<SavedState> {
                    return newArray(size)
                }
            }
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        return SavedState(superState, mCurrentHour, mCurrentMinute)
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        val ss = state as SavedState
        super.onRestoreInstanceState(ss.superState)
        currentHours = ss.hour
        currentMinutes = ss.minute
    }

    fun setOnTimeChangedListener(onTimeChangedListener: OnTimeChangedListener) {
        mOnTimeChangedListener = onTimeChangedListener
    }

    override fun getBaseline(): Int {
        return mHourPicker.baseline
    }

    private fun updateHourDisplay() {
        mHourPicker.value = mCurrentHour
        onTimeChanged()
    }

    private fun updateMinuteDisplay() {
        mMinutePicker.value = mCurrentMinute
        onTimeChanged()
    }

    private fun updateSecondsDisplay() {
        mSecondPicker.value = mCurrentSeconds
        onTimeChanged()
    }

    private fun onTimeChanged() {
        if (mOnTimeChangedListener != null)
            mOnTimeChangedListener?.onTimeChanged(this, currentHours, currentMinutes, currentSeconds)

        updateMaxValuesOnTimeChange()
    }

    private fun setSecondsMinValue(seconds: Int) {
        mSecondPicker.minValue = seconds
    }

    private fun setSecondsMaxValue(seconds: Int) {
        mSecondPicker.maxValue = seconds
    }

    private fun setMinutesMinValue(minutes: Int) {
        mMinutePicker.minValue = minutes
    }

    private fun setMinutesMaxValue(minutes: Int) {
        mMinutePicker.maxValue = minutes
    }

    private fun setHoursMinValue(hours: Int) {
        mHourPicker.minValue = hours
    }

    private fun setHoursMaxValue(hours: Int) {
        mHourPicker.maxValue = hours
    }

    fun setMaxValues(totalDurationSeconds: Int) {
        this.totalDurationInSeconds = totalDurationSeconds

        totalMaxHours = totalDurationSeconds / 3600
        totalMaxMinutes = totalDurationSeconds % 3600 / 60
        totalMaxSeconds = totalDurationSeconds % 60

        if (totalMaxSeconds < DEFAULT_SECONDS_MAX && totalMaxMinutes == 0 && totalMaxHours == 0)
            setSecondsMaxValue(totalMaxSeconds)

        if (totalMaxHours < 1) {
            mHourPicker.visibility = View.GONE
            mHourSeparator.visibility = View.GONE
        } else {
            setHoursMaxValue(totalMaxHours)
            mHourPicker.visibility = View.VISIBLE
            mHourSeparator.visibility = View.VISIBLE
        }

        if (totalMaxMinutes < 1) {
            mMinutePicker.visibility = View.GONE
            mMinuteSeparator.visibility = View.GONE
        } else {
            setMinutesMaxValue(totalMaxMinutes)
            mMinutePicker.visibility = View.VISIBLE
            mMinuteSeparator.visibility = View.VISIBLE
        }

        resetTime()
    }

    private fun updateMaxValuesOnTimeChange() {
        if (totalMaxHours == currentHours)
            setMinutesMaxValue(totalMaxMinutes)
        else
            setMinutesMaxValue(DEFAULT_MINUTES_MAX)

        if (totalMaxMinutes == currentMinutes)
            setSecondsMaxValue(totalMaxSeconds)
        else
            setSecondsMaxValue(DEFAULT_SECONDS_MAX)
    }

    private fun resetTime() {
        currentSeconds = 0
        currentMinutes = 0
        currentHours = 0
    }

    fun setMinValues(minDurationSeconds: Int) {

        minHours = minDurationSeconds / 3600
        minMinutes = minDurationSeconds % 3600 / 60
        minSeconds = minDurationSeconds % 60

        if (minSeconds > DEFAULT_SECONDS_MIN && totalMaxMinutes == 0 && totalMaxHours == 0)
            setSecondsMinValue(minSeconds)

        if (minMinutes > DEFAULT_MINUTES_MIN && totalMaxHours == 0)
            setMinutesMinValue(minMinutes)

        if (totalMaxHours > 0)
            setHoursMinValue(minHours)

        resetTime()
    }

    fun setMaxSeconds(maxValue: Int) {
        mSecondPicker.maxValue = maxValue
    }

    fun setMaxMinutes(maxValue: Int) {
        mMinutePicker.maxValue = maxValue
    }

    fun setMinSeconds(minValue: Int) {
        mSecondPicker.minValue = minValue
    }

    fun setMinMinutes(minValue: Int) {
        mMinutePicker.minValue = minValue
    }
}