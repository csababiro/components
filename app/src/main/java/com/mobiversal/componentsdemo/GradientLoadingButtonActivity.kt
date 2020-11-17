package com.mobiversal.componentsdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_gradient_loading_button.*

class GradientLoadingButtonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gradient_loading_button)

        glbtn_one.setLoadingState(true)
        glbtn_two.setOnClickListener {
            Toast.makeText(this, "Yesss", Toast.LENGTH_LONG).show()
        }
    }
}