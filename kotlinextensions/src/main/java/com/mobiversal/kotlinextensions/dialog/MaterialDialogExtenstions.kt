package com.mobiversal.kotlinextensions.dialog

/**
 * Created by Csaba on 8/28/2019.
 */

import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.DialogCallback
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.mobiversal.kotlinextensions.R


// Errors
fun Fragment.showErrorDialog(message: Int, positiveListener: DialogCallback? = null, negativeListener: DialogCallback? = null, dismissListener: DialogCallback? = null) {
    showDialog(this.getString(R.string.popup_title_error), this.getString(message), null, null, positiveListener, negativeListener)
}

fun Fragment.showErrorDialog(message: String, positiveListener: DialogCallback? = null, negativeListener: DialogCallback? = null, dismissListener: DialogCallback? = null) {
    showDialog(this.getString(R.string.popup_title_error), message, null, null, positiveListener, negativeListener)
}


// Info
fun Fragment.showInfoDialog(message: Int, positiveListener: DialogCallback? = null, negativeListener: DialogCallback? = null) {
    showInfoDialog(this.getString(message), positiveListener, negativeListener)
}

fun Fragment.showInfoDialog(message: String, positiveListener: DialogCallback? = null, negativeListener: DialogCallback? = null) {
    showDialog(getString(R.string.popup_title_info), message, null, null, positiveListener)
}

// Normal
fun Fragment.showDialog(title: Int, message: Int? = null, positive: Int? = null, negative: Int? = null, positiveListener: DialogCallback? = null, negativeListener: DialogCallback? = null, dismissListener: DialogCallback? = null) {
    showDialog(this.getString(title), message?.let { this.getString(it) }
            ?: run { "" }, positive?.let { this.getString(positive) }
            ?: null, negative?.let { this.getString(negative) }
            ?: null, positiveListener, negativeListener, dismissListener)
}

fun Fragment.showDialog(title: String, message: String? = null, positive: String? = null, negative: String? = null, positiveListener: DialogCallback? = null, negativeListener: DialogCallback? = null, dismissListener: DialogCallback? = null) {
    this.context?.let { context ->
        MaterialDialog(context)
                .cancelable(true)
                .title(text = title)
                .message(text = message ?: "")
                .positiveButton(text = positive
                        ?: this.getString(R.string.popup_action_ok)) { positiveListener?.invoke(it) }
                .negativeButton(text = negative
                        ?: this.getString(R.string.popup_action_cancel)) { negativeListener?.invoke(it) }
                .onDismiss { dismissListener?.invoke(it) }
                .show()
    }
}

fun Fragment.showOkDialog(title: Int, message: Int, positive: Int? = null, positiveListener: DialogCallback? = null, dismissListener: DialogCallback? = null, cancelable: Boolean = false) {
    showOkDialog(getString(title), getString(message), positive?.let { getString(it) }, positiveListener, dismissListener, cancelable)
}

fun Fragment.showOkDialog(title: String, message: String, positive: String? = null, positiveListener: DialogCallback? = null, dismissListener: DialogCallback? = null, cancelable: Boolean = false) {
    this.context?.let { context ->
        MaterialDialog(context)
                .title(text = title)
                .message(text = message)
                .positiveButton(
                        text = positive
                                ?: this.getString(R.string.popup_action_ok)
                ) { positiveListener?.invoke(it) }
                .cancelable(cancelable)
                .onDismiss { dismissListener?.invoke(it) }
                .show()
    }
}