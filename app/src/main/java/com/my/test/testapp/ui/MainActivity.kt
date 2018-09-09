package com.my.test.testapp.ui

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.OrientationEventListener
import android.widget.FrameLayout
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.my.test.testapp.MainApplication
import com.my.test.testapp.R
import com.my.test.testapp.di.component.ActivityComponent
import com.my.test.testapp.di.component.DaggerActivityComponent
import com.my.test.testapp.di.module.ActivityModule
import com.my.test.testapp.ui.feed.impl.RedditFeedViewImpl

class MainActivity : AppCompatActivity() {

    private lateinit var router: Router
    internal lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponent()
        setContentView(R.layout.activity_main)
        setupRouter(savedInstanceState)
    }

    private fun initComponent() {
        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent((application as MainApplication).applicationComponent)
                .activityModule(ActivityModule(this))
                .build()
    }

    private fun setupRouter(savedInstanceState: Bundle?) {
        val rootContainer = findViewById<FrameLayout>(R.id.root_container)
        router = Conductor.attachRouter(this, rootContainer, savedInstanceState)
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(RedditFeedViewImpl()))
        }
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        router.backstack.forEach {
            val controller = it.controller()
            if (controller is OrientationEventListener) {
                controller.onOrientationChanged(newConfig.orientation)
            }
        }
    }
}
