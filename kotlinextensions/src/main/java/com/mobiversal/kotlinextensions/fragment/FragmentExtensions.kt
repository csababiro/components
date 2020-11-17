@file:Suppress("UNCHECKED_CAST")

package com.mobiversal.kotlinextensions.fragment

/**
 * Created by Csaba on 8/28/2019.
 */

import androidx.fragment.app.Fragment


fun <T : Any> Fragment.argument(key: String) = kotlin.lazy {
    arguments?.get(key) as T
}

fun <T : Any> Fragment.argument(key: String, defaultValue: T? = null) = lazy {
    arguments?.get(key)  as? T ?: defaultValue
}
