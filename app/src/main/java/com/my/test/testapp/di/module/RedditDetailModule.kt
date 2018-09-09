package com.my.test.testapp.di.module

import android.annotation.SuppressLint
import android.content.Context
import com.my.test.testapp.di.scope.ActivityScope
import com.my.test.testapp.interactor.RedditDetailInteractor
import com.my.test.testapp.ui.detail.RedditDetailPresenter
import com.my.test.testapp.ui.detail.impl.RedditDetailPresenterImpl
import com.my.test.testapp.ui.detail.util.NotificationProgressView
import com.my.test.testapp.ui.detail.util.NotificationProgressViewImpl
import dagger.Module
import dagger.Provides

const val FOR_LANDSCAPE = "LayoutManager#forHorizontal"
const val FOR_PORTRAIT = "LayoutManager#forVertical"

@Module
class RedditDetailModule {

    @SuppressLint("ServiceCast")
    @Provides
    internal fun provideNotificatonProgressView(@ActivityScope context: Context): NotificationProgressView
            = NotificationProgressViewImpl(context)

    @Provides
    internal fun provideDetailPresenter(redditDetailInteractor: RedditDetailInteractor): RedditDetailPresenter
            = RedditDetailPresenterImpl(redditDetailInteractor)
}
