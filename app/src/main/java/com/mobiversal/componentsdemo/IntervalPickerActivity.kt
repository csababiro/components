package com.mobiversal.componentsdemo

import android.os.Bundle
import com.mobiversal.intervalpicker.IntervalPicker
import com.mobiversal.intervalpicker.OnTimeChangedListener
import com.mobiversal.kotlinextensions.logs.logDebug
import kotlinx.android.synthetic.main.activity_interval_picker.*

class IntervalPickerActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interval_picker)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        timeIntervalPicker()
    }

    private fun timeIntervalPicker() {
        intervalPicker.setMaxValues(12000) // 3h 20 min
        //intervalPicker.setMaxValues(150) // 2 min 30 sec

        intervalPicker.setOnTimeChangedListener(object: OnTimeChangedListener{
            override fun onTimeChanged(view: IntervalPicker, hour: Int, minute: Int, seconds: Int) {
                logDebug("Hour: $hour minute: $minute, seconds: $seconds")
            }
        })

        //intervalPicker.textColor = Color.RED
        intervalPicker.textColorResource = R.color.colorPrimary
        intervalPicker.textSizeResource = 24f
        intervalPicker.fontResource = R.style.BoldTextAppearance
    }
}
