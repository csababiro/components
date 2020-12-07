package com.mobiversal.componentsdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobiversal.videocapture.CameraFragment
import kotlinx.android.synthetic.main.activity_video_capture_demo.*

class VideoCaptureDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_capture_demo)

        supportFragmentManager.beginTransaction().add(R.id.videoCaptureContainer, CameraFragment()).commitAllowingStateLoss()
    }
}