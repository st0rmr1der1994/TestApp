package com.my.test.testapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import com.facebook.drawee.backends.pipeline.Fresco
import com.my.test.testapp.di.component.ApplicationComponent
import com.my.test.testapp.di.component.DaggerApplicationComponent
import com.my.test.testapp.di.module.ApplicationModule
import com.my.test.testapp.utils.NOTIFICATION_CHANNEL_ID
import com.my.test.testapp.utils.NOTIFICATION_CHANNEL_NAME

class MainApplication : Application() {

    internal lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
        initFresco()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupNotificationChannel()
        }
    }

    private fun initDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
        applicationComponent.inject(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupNotificationChannel() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.MAGENTA
        notificationManager.createNotificationChannel(notificationChannel)
    }


    private fun initFresco() = Fresco.initialize(this)
}
