package com.mobiversal.componentsdemo

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity


/**
 * Created by Csaba on 8/31/2019.
 */
open class ParentActivity : AppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}