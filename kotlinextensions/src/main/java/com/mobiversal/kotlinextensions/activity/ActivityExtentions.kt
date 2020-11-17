package com.mobiversal.kotlinextensions.activity

/**
 * Created by Csaba on 8/28/2019.
 */

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import android.content.Intent


fun Activity.share(title: String, message: String, shareType: String = "text/plain") {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        type = shareType
        putExtra(Intent.EXTRA_TEXT, message)
    }
    startActivity(Intent.createChooser(shareIntent, title))
}
