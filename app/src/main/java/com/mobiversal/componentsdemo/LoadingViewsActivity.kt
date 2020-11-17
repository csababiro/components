package com.mobiversal.componentsdemo

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_loading_views_activity.*

class LoadingViewsActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_views_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnLoadingView.setLoadingState(true)
        btnLoadingView.setOnClickListener{
            btnLoadingView.setLoadingState(!btnLoadingView.isInLoadingState)
        }
    }

}
