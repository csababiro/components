package com.mobiversal.componentsdemo

import android.os.Bundle

class KotlinExtensionsActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_extensions)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
