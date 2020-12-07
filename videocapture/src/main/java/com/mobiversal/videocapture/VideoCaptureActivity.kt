package com.mobiversal.videocapture

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.TextureView
import android.widget.ImageButton
import android.widget.Toast
import androidx.camera.core.CameraX
import androidx.camera.core.Preview
import androidx.camera.core.VideoCapture
import androidx.camera.core.impl.PreviewConfig
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner

private const val REQUEST_CODE_PERMISSIONS = 10
private val REQUIRED_PERMISSIONS =
    arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
private val tag = VideoCaptureActivity::class.java.simpleName

class VideoCaptureActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var viewFinder: TextureView
    private lateinit var captureButton: ImageButton
    private lateinit var videoCapture: VideoCapture

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_capture)

        viewFinder = findViewById(R.id.view_finder)
        captureButton = findViewById(R.id.capture_button)

// Request camera permissions
        if (allPermissionsGranted()) {
            viewFinder.post { startCamera() }
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                viewFinder.post { startCamera() }
            } else {
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted(): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(
                    this, permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    private fun startCamera() {
//        val preview = Preview.Builder().build()
//
//        preview.setOnPreviewOutputUpdateListener {
//            viewFinder.surfaceTexture = it.surfaceTexture
//        }
//
//        // Bind use cases to lifecycle
//        ProcessCameraProvider.bindToLifecycle(this, preview)
    }
}