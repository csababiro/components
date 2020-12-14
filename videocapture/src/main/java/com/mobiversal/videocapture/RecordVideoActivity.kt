package com.mobiversal.videocapture

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.example.cameraxbasic.utils.FLAGS_FULLSCREEN
import java.io.File

const val KEY_VIDEO_URI = "key_video_uri"

const val KEY_EVENT_ACTION = "key_event_action"
const val KEY_EVENT_EXTRA = "key_event_extra"
private const val IMMERSIVE_FLAG_TIMEOUT = 500L

class RecordVideoActivity : AppCompatActivity() {

    private lateinit var container: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_video)
        container = findViewById(R.id.fragment_container)
    }

    override fun onResume() {
        super.onResume()
        // Before setting full screen flags, we must wait a bit to let UI settle; otherwise, we may
        // be trying to set app to immersive mode before it's ready and the flags do not stick
        container.postDelayed({ container.systemUiVisibility = FLAGS_FULLSCREEN }, IMMERSIVE_FLAG_TIMEOUT)
    }

    companion object {

        // need new way to pass to the fragment
        lateinit var videoParams: RecordVideoParams

        fun openForResult(activity: Activity, requestCode: Int, params: RecordVideoParams) {
            videoParams = params
            val intent = Intent(activity, RecordVideoActivity::class.java)
            activity.startActivityForResult(intent, requestCode)
        }

        fun openForResult(fragment: Fragment, requestCode: Int, params: RecordVideoParams) {
            videoParams = params
            val intent = Intent(fragment.context, RecordVideoActivity::class.java)
            fragment.startActivityForResult(intent, requestCode)
        }

        /** Use external media if it is available, our app's file directory otherwise */
        fun getOutputDirectory(context: Context): File {
            val appContext = context.applicationContext
            val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
                File(it, appContext.resources.getString(R.string.app_name)).apply { mkdirs() }
            }
            return if (mediaDir != null && mediaDir.exists())
                mediaDir else appContext.filesDir
        }
    }

    override fun finish() {
        sendResultBack()
        super.finish()
    }

    private fun sendResultBack() {
        val resultIntent = Intent()
        val uri = getVideoUri()
        resultIntent.putExtra(KEY_VIDEO_URI, uri)
        setResult(Activity.RESULT_OK, resultIntent)
    }

    private fun getVideoUri(): Uri? {
        val cameraFragment = getCameraFragment()
        cameraFragment.let {
            return it?.savedUri
        }
    }

    private fun getCameraFragment() : RecordVideoFragment? {
        val fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.fragment_container)
        fragment?.let {
            val fragments = it.childFragmentManager.fragments
            if (fragments != null && fragments.size > 0) {
                val myFragment = fragments[0]
                if (myFragment is RecordVideoFragment)
                    return myFragment
            }
        }
        return null
    }
}