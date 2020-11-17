package com.mobiversal.kotlinextensions.logs

/**
 * Created by Csaba on 8/28/2019.
 */

import android.util.Log
import com.mobiversal.kotlinextensions.BuildConfig

const val APP_NAME = "APP_NAME"

// TODO use the logging library
fun <T : Any> T.logDebug(message: String, tag: String? = null) {
    val logTag: String = tag?.let { it } ?: run { javaClass.simpleName }
    if (BuildConfig.DEBUG) {
        val tagString = "$APP_NAME [$logTag] "
        logD(tagString, message)
    }
}

fun <T : Any> T.logError(message: String, tag: String? = null) {
    val logTag: String = tag?.let { it } ?: run { javaClass.simpleName }
    if (BuildConfig.DEBUG) {
        val tagString = "$APP_NAME [$logTag] "
        logE(tagString, message)
    }
}

private fun logD(tag: String, logMsg: String) {
    if (logMsg.length > 4000) {
        Log.d(tag, logMsg.substring(0, 4000))
        logD(tag, logMsg.substring(4000))
    } else
        Log.d(tag, logMsg)
}

private fun logE(tag: String, logMsg: String) {
    if (logMsg.length > 4000) {
        Log.e(tag, logMsg.substring(0, 4000))
        logE(tag, logMsg.substring(4000))
    } else
        Log.e(tag, logMsg)
}

