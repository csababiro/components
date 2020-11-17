package com.mobiversal.searchbar

import android.view.View

/**
 * Created by Csaba on 8/8/2019.
 */

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
