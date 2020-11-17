package com.mobiversal.searchbar

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * Created by Csaba on 8/8/2019.
 */
fun EditText.onChange(onChangeFunction: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            onChangeFunction(s?.toString() ?: "")
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}