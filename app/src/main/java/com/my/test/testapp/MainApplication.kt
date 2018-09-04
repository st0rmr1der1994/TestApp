package com.my.test.testapp

import android.app.Application
import com.my.test.testapp.di.component.ApplicationComponent
import com.my.test.testapp.di.component.DaggerApplicationComponent
import com.my.test.testapp.di.module.ApplicationModule
import io.realm.Realm
import io.realm.RealmConfiguration

class MainApplication : Application() {

    internal lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        initRealm()
        initDagger()
    }

    private fun initRealm() {
        Realm.init(this)
        val config = RealmConfiguration.Builder().name("testapp.realm").build()
        Realm.setDefaultConfiguration(config)
    }

    private fun initDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
        applicationComponent.inject(this)
    }
}
