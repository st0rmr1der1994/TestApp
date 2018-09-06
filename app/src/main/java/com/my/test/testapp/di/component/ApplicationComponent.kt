package com.my.test.testapp.di.component

import com.my.test.testapp.MainApplication
import com.my.test.testapp.di.module.ApplicationModule
import com.my.test.testapp.interactor.RedditDetailInteractor
import com.my.test.testapp.interactor.RedditFeedInteractor
import dagger.Component

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun application(): MainApplication

    fun feedInteractor(): RedditFeedInteractor

    fun detailInteractor(): RedditDetailInteractor

    fun inject(application: MainApplication)
}
