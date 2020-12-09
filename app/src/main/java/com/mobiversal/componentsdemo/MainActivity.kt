package com.mobiversal.componentsdemo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.mobiversal.kotlinextensions.activity.launchActivity
import com.mobiversal.videocapture.VideoCaptureActivity

class MainActivity : ParentActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.new_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_spannable_checkbox ->
                launchActivity<SpannableCheckboxActivity>()
            R.id.nav_loading_views ->
                launchActivity<LoadingViewsActivity>()
            R.id.nav_search_bar ->
                launchActivity<SearchBarActivity>()
            R.id.nav_interval_picker ->
                launchActivity<IntervalPickerActivity>()
            R.id.nav_gradient_text_view ->
                launchActivity<GradientTextViewActivity>()
            R.id.nav_gradient_button ->
                launchActivity<GradientButtonActivity>()
            R.id.nav_popup_window_custom ->
                launchActivity<PopupWindowCustomActivity>()
            R.id.nav_kotlin_extensions ->
                launchActivity<KotlinExtensionsActivity>()
            R.id.nav_gradient_loading_button ->
                launchActivity<GradientLoadingButtonActivity>()
            R.id.nav_video_capture ->
                launchActivity<VideoCaptureActivity>()
            R.id.nav_spannable_text_view ->
                launchActivity<SpannableTextViewActivity>()
            R.id.nav_see_more ->
                launchActivity<SeeMoreActivity>()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
