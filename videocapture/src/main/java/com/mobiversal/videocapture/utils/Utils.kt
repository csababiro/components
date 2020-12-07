package com.mobiversal.videocapture.utils

import android.content.Context
import java.io.File

/**
 * Created by Biro Csaba on 07/12/2020.
 */
class Utils {

    companion object {
        /** Use external media if it is available, our app's file directory otherwise */
        fun getOutputDirectory(context: Context, appNameFolder: String): File {
            val appContext = context.applicationContext
            val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
                File(it, appNameFolder).apply { mkdirs() } }
            return if (mediaDir != null && mediaDir.exists())
                mediaDir else appContext.filesDir
        }
    }
}