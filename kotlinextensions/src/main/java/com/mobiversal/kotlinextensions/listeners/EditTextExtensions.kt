package com.mobiversal.kotlinextensions.listeners

/**
 * Created by Csaba on 8/28/2019.
 */

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

// https://medium.com/@bharathkumarbachina/avoid-boilerplate-code-with-kotlin-extensions-in-android-6a66e74787f2
fun EditText.onChange(cb: (String) -> Unit) {
    this.addTextChangedListener(object: TextWatcher {

        override fun afterTextChanged(s: Editable?) {
            cb(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    })
}