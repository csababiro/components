package com.mobiversal.intervalpicker

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat



class CustomNumberPicker(context: Context, attrs: AttributeSet) : android.widget.NumberPicker(context, attrs) {

    var textSizeResource = 0f
    var textColorResource = 0
    var errorTextColorResource = 0
    var textColor = 0
    var errorTextColor = 0
    var fontResource = 0

    override fun addView(child: View) {
        super.addView(child)
        updateView(child)
    }

    override fun addView(child: View, index: Int, params: android.view.ViewGroup.LayoutParams) {
        super.addView(child, index, params)
        updateView(child)
    }

    override fun addView(child: View, params: android.view.ViewGroup.LayoutParams) {
        super.addView(child, params)
        updateView(child)
    }

    fun updateView() {
        val count = getChildCount()
        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child is EditText)
                updateView(child)
        }
        invalidate()
    }

    private fun updateView(view: View) {
        if (view is EditText) {

            val context = view.context

            @Suppress("DEPRECATION")
            if (fontResource != 0)
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                    view.setTextAppearance(context, fontResource)
                else
                    view.setTextAppearance(fontResource)

            view.setTextColor(getColors())

            if (textSizeResource != 0f)
                view.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeResource)
        }
    }

    private fun getColors(): ColorStateList {

        var colors: ColorStateList? = null
        if (textColorResource != 0) {
            if (errorTextColorResource == 0)
                errorTextColorResource = textColorResource
            colors = ColorStateList(
                arrayOf(intArrayOf(android.R.attr.state_selected), intArrayOf(-android.R.attr.state_selected)),
                intArrayOf(
                    ContextCompat.getColor(context, errorTextColorResource),
                    ContextCompat.getColor(context, textColorResource)
                )
            )
        } else
            if (textColorResource != 0) {
                colors = ColorStateList(
                    arrayOf(
                        intArrayOf(android.R.attr.state_selected),
                        intArrayOf(-android.R.attr.state_selected)
                    ), intArrayOf(errorTextColor, textColor)
                )
            } else
                colors = ColorStateList(
                    arrayOf(
                        intArrayOf(android.R.attr.state_selected),
                        intArrayOf(-android.R.attr.state_selected)
                    ), intArrayOf(Color.RED, Color.BLACK)
                )

        return colors
    }
}