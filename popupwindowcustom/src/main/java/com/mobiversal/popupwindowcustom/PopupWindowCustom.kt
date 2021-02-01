package com.mobiversal.popupwindowcustom

import android.os.Build
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.popup_window.view.*
import kotlinx.android.synthetic.main.row_popup_window.view.*

const val NO_ITEM_SELECTED = -1

class PopupWindowCustom(
    private val items: List<PopupWindowItem>,
    private val popupWindowResponse: PopupWindowResponse?
) {

    private var selectedIndex: Int = NO_ITEM_SELECTED

    var largeView: Boolean = false
    var gravity = Gravity.RIGHT or Gravity.TOP
    var topMargin = 0

    var showSelectedCheck = false

    var textSizeResource = 0f
    var textColorResource = 0
    var textColor = 0
    var fontResource = 0

    fun showPopupWindow(anchorView: View) {

        if (items == null)
            return

        val popupWindow = PopupWindow()
        applyParams(popupWindow)
        popupWindow.contentView = getContent(popupWindow, anchorView)

        // place the popup window
        val coordinates = getCoordinates(anchorView)
        popupWindow.showAtLocation(anchorView, gravity, coordinates.x, coordinates.y)
    }

    private fun getCoordinates(anchorView: View): XYCoordinates {
        val coordinates = XYCoordinates()
        val location = IntArray(2)
        anchorView.getLocationInWindow(location)

        val dp16 = 16.pxToDp()
        val rightDistance = dp16 - 5.5f.pxToDp()
        val topDistance = location[1] + dp16 + topMargin

        val x = rightDistance + 196.pxToDp()
        val y = topDistance - 8.pxToDp()

        coordinates.x = x
        coordinates.y = y

        return coordinates
    }

    private fun applyParams(popupWindow: PopupWindow) {
        popupWindow.animationStyle = R.style.PopupAnimationStyle
        popupWindow.width = if (largeView) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
        popupWindow.height = ViewGroup.LayoutParams.WRAP_CONTENT
//        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true

    }

    private fun getContent(popupWindow: PopupWindow, anchorView: View): View {
        val content = LayoutInflater.from(anchorView.context).inflate(R.layout.popup_window, null)

        createRows(content, popupWindow)

        return content
    }

    private fun createRows(content: View, popupWindow: PopupWindow) {
        val length = items.size
        for (i in 0 until length) {
            val popupRow = LayoutInflater.from(content.context).inflate(R.layout.row_popup_window, null)
            val item = items[i]

            displayRowText(popupRow, item, i)

            displayRowIcon(popupRow, item, i)

            displayRowSelectedState(popupRow, item, i)

            setSelectionListener(popupWindow, popupRow, item, i)

            val layout = content.profile_popup_layout
            layout.addView(popupRow)
        }
    }

    private fun displayRowText(popupRow: View, item: PopupWindowItem, i: Int) {
        val popupText = popupRow.txt

        applyTextProperties(popupText)

        val valueResource = item.valueResource
        if (valueResource != 0)
            popupText.setText(valueResource)
        else
            popupText.text = item.value

        popupRow.tag = i
    }

    private fun applyTextProperties(popupTextView: TextView) {

        val context = popupTextView.context

        @Suppress("DEPRECATION")
        if (fontResource != 0)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                popupTextView.setTextAppearance(context, fontResource)
            else
                popupTextView.setTextAppearance(fontResource)

        if (textColorResource != 0)
            popupTextView.setTextColor(ContextCompat.getColor(context, textColorResource))
        else
            if (textColor != 0)
                popupTextView.setTextColor(textColor)

        if (textSizeResource != 0f)
            popupTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeResource)
    }

    private fun displayRowIcon(popupRow: View, item: PopupWindowItem, i: Int) {
        if (item.iconResource != 0) {
            popupRow.img.setImageResource(item.iconResource)
            popupRow.img.visibility = View.VISIBLE
        }
    }

    private fun displayRowSelectedState(popupRow: View, item: PopupWindowItem, i: Int) {

        if (showSelectedCheck) {
            if (item.isSelected) {
                popupRow.imgSelected.visibility = View.VISIBLE
                selectedIndex = i
            } else
                popupRow.imgSelected.visibility = View.INVISIBLE
        } else
            popupRow.imgSelected.visibility = View.GONE

    }

    private fun setSelectionListener(popupWindow: PopupWindow, popupRow: View, item: PopupWindowItem, i: Int) {
        popupRow.setOnClickListener {
            // deselect previous element
            if (selectedIndex != NO_ITEM_SELECTED)
                items[selectedIndex].isSelected = false

            item.isSelected = true
            selectedIndex = i
            popupWindowResponse?.selectedResourceId(item)
            popupWindow.dismiss()
        }
    }

    fun getSelectedIndex(): Int {
        return selectedIndex
    }

    fun setSelectedIndex(selectedIndex: Int) {
        // reset previous selected item
        items!![this.selectedIndex].isSelected = false
        this.selectedIndex = selectedIndex
        // set current selected item
        items[selectedIndex].isSelected = true
    }
}