package com.my.test.testapp.di.component

import android.content.Context
import com.my.test.testapp.MainApplication
import com.my.test.testapp.di.module.ApplicationModule
import com.my.test.testapp.interactor.RedditDetailInteractor
import com.my.test.testapp.interactor.RedditFeedInteractor
import com.my.test.testapp.service.network.util.DownloadProgressListener
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun context(): Context

    fun feedInteractor(): RedditFeedInteractor

    fun detailInteractor(): RedditDetailInteractor

    fun downloadProgress(): DownloadProgressListener

    fun inject(application: MainApplication)
}
