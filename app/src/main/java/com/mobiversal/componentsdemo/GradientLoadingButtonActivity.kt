package com.mobiversal.componentsdemo

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.SuperscriptSpan
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_gradient_loading_button.*

class GradientLoadingButtonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gradient_loading_button)

        glbtn_one.setLoadingState(true)
        glbtn_two.setOnClickListener {
            Toast.makeText(this, "Yesss", Toast.LENGTH_LONG).show()
        }

        val string = SpannableString("56s")
        val end = string.length
        val start = end - 1
        string.setSpan(SuperscriptSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        glbtn_five.setSpannableTextBLV(string)
    }
}