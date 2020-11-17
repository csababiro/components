package com.mobiversal.kotlinextensions.view

/**
 * Created by Csaba on 8/28/2019.
 */

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode


@SuppressLint("RestrictedApi")
fun BottomNavigationView.disableShiftMode() {
    val menuView = getChildAt(0) as BottomNavigationMenuView
    try {
        for (i in 0 until menuView.childCount) {
            val item = menuView.getChildAt(i) as BottomNavigationItemView
            val label = item.findViewById<View>(com.google.android.material.R.id.largeLabel)
            if (label is TextView)
                label.setPadding(0, 0, 0, 0)
            item.setShifting(false)
            item.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED)
            // set once again checked value, so view will be updated
            item.setChecked(item.itemData.isChecked)
        }
    } catch (e: NoSuchFieldException) {
        Log.e("BottomNavigationViewExt", "Unable to get shift mode field", e)
    } catch (e: IllegalStateException) {
        Log.e("BottomNavigationViewExt", "Unable to change value of shift mode", e)
    }
}