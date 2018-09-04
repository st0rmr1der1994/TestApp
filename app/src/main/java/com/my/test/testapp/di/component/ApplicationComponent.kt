package com.my.test.testapp.di.component

import android.content.Context
import com.my.test.testapp.MainApplication
import com.my.test.testapp.di.module.ApplicationModule
import dagger.Component

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun appContext(): Context

    fun application(): MainApplication

    fun inject(application: MainApplication)
}
