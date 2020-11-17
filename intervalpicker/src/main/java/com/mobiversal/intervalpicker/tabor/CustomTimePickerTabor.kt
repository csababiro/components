package com.mobiversal.intervalpicker.tabor

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.NumberPicker
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.mobiversal.intervalpicker.R
import java.util.*

class CustomTimePickerTabor @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : FrameLayout(context, attrs, defStyle) {

    val MINUTES_INTERVAL = 15

    // state
    private var mCurrentHour = 0 // 0-n
    private var mCurrentMinute = 0 // 0-59

    // ui components
    private val mHourPicker: CustomNumberPickerTabor
    private val mMinutePicker: CustomNumberPickerTabor
    private val mHourSeparator: TextView

    // callbacks
    private var mOnTimeChangedListener: OnTimeChangedListener? = null

    /**
     * @return The current hour (0-n).
     */
    /**
     * Set the current hour.
     */
    var currentHour: Int
        get() = mCurrentHour
        set(currentHour) {
            this.mCurrentHour = currentHour
            updateHourDisplay()
        }

    /**
     * @return The current minute.
     */
    /**
     * Set the current minute (0-59).
     */
    var currentMinute: Int
        get() = mCurrentMinute
        set(currentMinute) {
            this.mCurrentMinute = currentMinute!!
            updateMinuteDisplay()
        }

    var currentValue: Int
        get() = mMinutePicker.getValue()
        set(index) {
            mMinutePicker.setValue(index)
        }

    /**
     * The callback interface used to indicate the time has been adjusted.
     */
    interface OnTimeChangedListener {

        /**
         * @param view The view associated with this listener.
         * @param hour The current hour.
         * @param minutes The current minute.
         */
        fun onTimeChanged(view: CustomTimePickerTabor, hour: Int, minutes: Int)
    }

    init {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.custom_time_picker_widget_tabor, this, true)

        // hour
        mHourPicker = findViewById(R.id.hour) as CustomNumberPickerTabor
        mHourSeparator = findViewById(R.id.hour_separator) as TextView
        val hourValues = arrayOf("06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23")
        mHourPicker.setMinValue(0)
        mHourPicker.setMaxValue(hourValues.size - 1)
        mHourPicker.setDisplayedValues(hourValues)
        mHourPicker.setFormatter(TWO_DIGIT_FORMATTER)
        setDividerColorToGrey(mHourPicker)
        mHourPicker.setOnValueChangedListener(NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->
            mCurrentHour = newVal
            onTimeChanged()
        })

        // digits of minute
        mMinutePicker = findViewById(R.id.minute) as CustomNumberPickerTabor
        //val minValues = arrayOf("00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55")
        val minValues = arrayOf("00", "15", "30", "45")
        mMinutePicker.setMinValue(0)
        mMinutePicker.setMaxValue(minValues.size - 1)
        mMinutePicker.setDisplayedValues(minValues)
        mMinutePicker.setValue(0)
        //        setMinutesMinValue(5);
        mMinutePicker.setFormatter(TWO_DIGIT_FORMATTER)
        setDividerColorToGrey(mMinutePicker)
        mMinutePicker.setOnValueChangedListener(NumberPicker.OnValueChangeListener { spinner, oldVal, newVal ->
            mCurrentMinute = newVal
            onTimeChanged()
        })

        setOnTimeChangedListener(NO_OP_CHANGE_LISTENER)

        if (!isEnabled) {
            isEnabled = false
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        mMinutePicker.setEnabled(enabled)
        mHourPicker.setEnabled(enabled)
    }

    /**
     * Used to save / restore state of time picker
     */
    private class SavedState : View.BaseSavedState {

        val hour: Int
        val minute: Int

        constructor(superState: Parcelable, hour: Int, minute: Int) : super(superState) {
            this.hour = hour
            this.minute = minute
        }

        private constructor(`in`: Parcel) : super(`in`) {
            hour = `in`.readInt()
            minute = `in`.readInt()
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            super.writeToParcel(dest, flags)
            dest.writeInt(hour)
            dest.writeInt(minute)
        }

        companion object {

            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(`in`: Parcel): SavedState {
                    return SavedState(`in`)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
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
        super.onRestoreInstanceState(ss.getSuperState())
        currentHour = ss.hour
        currentMinute = ss.minute
    }

    /**
     * Set the callback that indicates the time has been adjusted by the user.
     * @param onTimeChangedListener the callback, should not be null.
     */
    fun setOnTimeChangedListener(onTimeChangedListener: OnTimeChangedListener?) {
        mOnTimeChangedListener = onTimeChangedListener
    }

    override fun getBaseline(): Int {
        return mHourPicker.getBaseline()
    }

    /**
     * Set the state of the spinners appropriate to the current hour.
     */
    private fun updateHourDisplay() {
        mHourPicker.setValue(mCurrentHour)
        onTimeChanged()
    }

    private fun onTimeChanged() {
        mOnTimeChangedListener?.onTimeChanged(this, currentHour!!, currentMinute!! * MINUTES_INTERVAL)
    }

    /**
     * Set the state of the spinners appropriate to the current minute.
     */
    private fun updateMinuteDisplay() {
        mMinutePicker.setValue(mCurrentMinute)
        mOnTimeChangedListener?.onTimeChanged(this, currentHour!!, currentMinute!!)
    }

    fun setMinutesMinValue(minutes: Int) {
        if (minutes == 5) {
            val minValues1 = arrayOf("00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55")
            mMinutePicker.setDisplayedValues(minValues1)
            mMinutePicker.setMinValue(0)
            mMinutePicker.setMaxValue(minValues1.size - 2)
            val minValues = arrayOf("05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55")
            mMinutePicker.setDisplayedValues(minValues)
            //if(mMinutePicker.getValue() > - 1)
            //   mMinutePicker.setValue(mMinutePicker.getValue() - 1);
        } else {
            val minValues = arrayOf("00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55")
            mMinutePicker.setDisplayedValues(minValues)
            mMinutePicker.setMinValue(0)
            mMinutePicker.setMaxValue(minValues.size - 1)
            //  if(mMinutePicker.getValue() < minValues.length - 1)
            //            if(mMinutePicker.getValue() == 0)
            //            mMinutePicker.setValue(mMinutePicker.getValue() + 1);
        }

        //        String[] minValues = new String[]{ "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};
        //        if(minutes == 5)
        //            configurePicker(mMinutePicker, minValues, 0, minValues.length -1, 0);
        //        else
        //            configurePicker(mMinutePicker, minValues, 1, minValues.length -1, 0);
    }

    private fun configurePicker(picker: NumberPicker, values: Array<String>, minValue: Int, maxValue: Int, currentValue: Int) {
        picker.displayedValues = values //to avoid out of bounds
        picker.minValue = minValue
        picker.maxValue = maxValue
        picker.value = currentValue
        picker.displayedValues = Arrays.asList(*values).subList(minValue, maxValue - 1).toTypedArray()
    }

    fun setMinutesMaxValue(minutes: Int) {
        mMinutePicker.setMaxValue(minutes)
    }

    private fun setDividerColorToGrey(picker: NumberPicker) {

        val pickerFields = NumberPicker::class.java.declaredFields
        for (pf in pickerFields) {
            if (pf.name == "mSelectionDivider") {
                pf.isAccessible = true
                try {
                    val colorDrawable = ColorDrawable(ContextCompat.getColor(picker.context, R.color.number_picker_divider))
                    pf.set(picker, colorDrawable)
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                } catch (e: Resources.NotFoundException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

                break
            }
        }
    }

    companion object {

        /**
         * A no-op callback used in the constructor to avoid null checks
         * later in the code.
         */
        private val NO_OP_CHANGE_LISTENER = object : OnTimeChangedListener {
            override fun onTimeChanged(view: CustomTimePickerTabor, hour: Int, minutes: Int) {}
        }

        val TWO_DIGIT_FORMATTER: NumberPicker.Formatter = NumberPicker.Formatter { value -> String.format("%2d", value) }
    }
}