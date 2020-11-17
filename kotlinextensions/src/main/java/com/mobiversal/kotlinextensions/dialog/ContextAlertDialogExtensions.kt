package com.mobiversal.kotlinextensions.dialog

/**
 * Created by Csaba on 8/28/2019.
 */

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.text.SpannableString
import com.afollestad.materialdialogs.DialogCallback
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.mobiversal.kotlinextensions.R

// Errors
fun Context.showErrorDialog(message: String, positiveListener: DialogCallback? = null, dismissListener: DialogCallback? = null) {
    showOkDialog(this.getString(R.string.popup_title_error), message,  null, positiveListener, dismissListener)
}

// Info
fun Context.showInfoDialog(message: Int, positiveListener: DialogCallback? = null, negativeListener: DialogCallback? = null) {
    showInfoDialog(this.getString(message), positiveListener, negativeListener)
}

fun Context.showInfoDialog(message: String, positiveListener: DialogCallback? = null, negativeListener: DialogCallback? = null) {
    showDialog(getString(R.string.popup_title_info), message, null, null, positiveListener)
}

// Normal
fun Context.showDialog(title: Int, message: Int, positive: Int? = null, negative: Int? = null, positiveListener: DialogCallback? = null, negativeListener: DialogCallback? = null, dismissListener: DialogCallback? = null) {
    showDialog(this.getString(title), this.getString(message), positive?.let { this.getString(positive) }
            ?: null, negative?.let { this.getString(negative) }
            ?: null, positiveListener, negativeListener, dismissListener)
}

fun Context.showDialog(title: String, message: String, positive: String? = null, negative: String? = null, positiveListener: DialogCallback? = null, negativeListener: DialogCallback? = null, dismissListener: DialogCallback? = null) {

    MaterialDialog(this)
            .cancelable(true)
            .title(text = title)
            .message(text = message)
            .positiveButton(text = positive
                    ?: this.getString(R.string.popup_action_ok)) { positiveListener?.invoke(it) }
            .negativeButton(text = negative
                    ?: this.getString(R.string.popup_action_cancel)) { negativeListener?.invoke(it) }
            .onDismiss{ dismissListener?.invoke(it) }
            .show()
}

fun Context.showOkDialog(title: Int, message: String, positive: String? = null, positiveListener: DialogCallback? = null, dismissListener: DialogCallback? = null) {
    showOkDialog(getString(title), message, positive, positiveListener, dismissListener)
}

fun Context.showOkDialog(title: Int, message: Int, positive: String? = null, positiveListener: DialogCallback? = null, dismissListener: DialogCallback? = null) {
    showOkDialog(getString(title), getString(message), positive, positiveListener, dismissListener)
}

fun Context.showOkDialog(title: String, message: String, positive: String? = null, positiveListener: DialogCallback? = null, dismissListener: DialogCallback? = null) {

    MaterialDialog(this)

            .title(text = title)
            .message(text = message)
            .positiveButton(text = positive
                    ?: this.getString(R.string.popup_action_ok)) { positiveListener?.invoke(it) }
            .cancelable(false)
            .onDismiss{ dismissListener?.invoke(it) }
            .show()
}

fun Context.showSpannableDialog(title: String, message: SpannableString, positive: String? = null, negative: String? = null, positiveListener: DialogCallback? = null, negativeListener: DialogCallback? = null) {

    MaterialDialog(this)
            .title(text = title)
            .message(text = message)
            .positiveButton(text = positive
                    ?: this.getString(R.string.popup_action_ok)) { positiveListener?.invoke(it) }
            .negativeButton(text = negative
                    ?: this.getString(R.string.popup_action_cancel)) { negativeListener?.invoke(it) }
            .cancelable(true)
            .show()
}



