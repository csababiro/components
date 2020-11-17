package com.mobiversal.componentsdemo

import android.graphics.Color
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_gradient_text_view.*

class GradientTextViewActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gradient_text_view)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        gtxt_one.setGradientStartColor(Color.RED)
        gtxt_one.setGradientEndColor(Color.GREEN)
    }
}
