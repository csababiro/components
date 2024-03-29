package com.mobiversal.kotlinextensions.activity

/**
 * Created by Csaba on 8/28/2019.
 */

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.content.Context
import android.os.Build

/**
 * Kotlin Extensions for simpler, easier and funw way
 * of launching of Activities
 */
//https://android.jlelse.eu/launching-activities-in-easier-way-using-kotlin-extensions-121a8175220c

inline fun <reified T : Any> Activity.launchActivity (
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {})
{
    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
    {
        startActivityForResult(intent, requestCode, options)
    } else {
        startActivityForResult(intent, requestCode)
    }
}

inline fun <reified T : Any> Context.launchActivity (
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {})
{
    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
    {
        startActivity(intent, options)
    } else {
        startActivity(intent)
    }
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)

//// Examples
/// Simple Activities
//launchActivity<UserDetailActivity>()
//
//// Add Intent extras
//launchActivity<UserDetailActivity> {
//    putExtra(INTENT_USER_ID, user.id)
//}
//
//// Add custom flags
//launchActivity<UserDetailActivity> {
//    putExtra(INTENT_USER_ID, user.id)
//    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//}
//
//// Add Shared Transistions
//val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, avatar, "avatar")
//launchActivity<UserDetailActivity>(options = options) {
//    putExtra(INTENT_USER_ID, user.id)
//}
//
//// Add requestCode for startActivityForResult() call
//launchActivity<UserDetailActivity>(requestCode = 1234) {
//    putExtra(INTENT_USER_ID, user.id)
//}