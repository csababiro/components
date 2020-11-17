package com.mobiversal.componentsdemo

import android.os.Bundle

class SearchBarActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
