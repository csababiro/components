package com.mobiversal.kotlinextensions.fragment

/**
 * Created by Csaba on 8/28/2019.
 */

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


fun FragmentManager.findOrAdd(@IdRes containerId: Int, fragment: Fragment, tag: String? = null) {
    val cachedFragment = this.findFragmentByTag(tag)
    if (cachedFragment == null) {
        this.beginTransaction()
                .add(containerId, fragment, tag)
                .commit()
    }
}