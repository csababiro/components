package com.mobiversal.kotlinextensions.view

/**
 * Created by Csaba on 8/28/2019.
 */

import android.view.View
import com.mobiversal.kotlinextensions.view.DebounceClickListener

fun View.setOnDebounceClickListener(clickListener: (View) -> Unit) {
    this.setOnClickListener(object : DebounceClickListener() {
        override fun onDebouncedClick(v: View) {
            clickListener(v)
        }
    })
}

fun View.enable() {
    this.isEnabled = true
}

fun View.disable() {
    this.isEnabled = false
}

fun View.select() {
    this.isSelected = true
}

fun View.deselect() {
    this.isSelected = false
}

fun View.visible(alpha: Float = 1f) {
    this.alpha = alpha
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visibleAnimated(alpha: Float = 1f) {
    this.animate()
        .setDuration(200L)
        .alpha(alpha)
        .withStartAction { this.visibility = View.VISIBLE }
        .start()
}

fun View.invisibleAnimated() {
    this.animate()
        .setDuration(200L)
        .alpha(0f)
        .withEndAction { this.visibility = View.INVISIBLE }
        .start()
}

fun View.goneAnimated() {
    this.animate()
        .setDuration(200L)
        .alpha(0f)
        .withEndAction { this.visibility = View.GONE }
        .start()
}

fun View.visibleOrInvisible(visible: Boolean) {
    if (visible)
        visible()
    else
        invisible()
}

fun View.visibleOrGone(visible: Boolean) {
    if (visible)
        visible()
    else
        gone()
}
