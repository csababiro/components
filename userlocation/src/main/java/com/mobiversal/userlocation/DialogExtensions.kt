package com.mobiversal.userlocation

import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.DialogCallback
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss

/**
 * Created by Biro Csaba on 07/12/2020.
 */

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