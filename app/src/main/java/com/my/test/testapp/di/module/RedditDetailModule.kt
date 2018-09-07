package com.my.test.testapp.di.module

import android.annotation.SuppressLint
import android.content.Context
import com.my.test.testapp.interactor.RedditDetailInteractor
import com.my.test.testapp.ui.detail.RedditDetailPresenter
import com.my.test.testapp.ui.detail.impl.RedditDetailPresenterImpl
import com.my.test.testapp.ui.detail.util.NotificationProgressView
import com.my.test.testapp.ui.detail.util.NotificationProgressViewImpl
import dagger.Module
import dagger.Provides

@Module
class RedditDetailModule {

    @SuppressLint("ServiceCast")
    @Provides
    internal fun provideNotificatonProgressView(context: Context): NotificationProgressView
            = NotificationProgressViewImpl(context)

    @Provides
    internal fun provideDetailPresenter(redditDetailInteractor: RedditDetailInteractor)
            : RedditDetailPresenter = RedditDetailPresenterImpl(redditDetailInteractor)
}
