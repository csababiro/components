package com.mobiversal.componentsdemo

import android.os.Bundle

class GradientButtonActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gradient_button)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
