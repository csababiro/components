package com.mobiversal.intervalpicker.tabor

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.mobiversal.intervalpicker.R

class CustomNumberPickerTabor(context: Context, attrs: AttributeSet) : android.widget.NumberPicker(context, attrs) {

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

    private fun updateView(view: View) {
        if (view is EditText) {
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.number_picker_font_size))
            view.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryTaborApp))
            //view.typeface = ResourcesCompat.getFont(context,R.font.hindvadodara_semibold) TODO font

            val colors = ColorStateList(
                    arrayOf(intArrayOf(android.R.attr.state_selected), intArrayOf(-android.R.attr.state_selected)),
                    intArrayOf(ContextCompat.getColor(context, R.color.edit_text_error_red), ContextCompat.getColor(context, R.color.colorPrimaryTaborApp))
            )

            view.setTextColor(colors)
        }
    }
}