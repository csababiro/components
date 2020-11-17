package com.mobiversal.componentsdemo

import android.os.Bundle

class SpannableCheckboxActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spannable_checkbox)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
