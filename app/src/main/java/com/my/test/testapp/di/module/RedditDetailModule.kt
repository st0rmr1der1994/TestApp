package com.my.test.testapp.di.module

import android.annotation.SuppressLint
import android.content.Context
import com.my.test.testapp.di.scope.ActivityScope
import com.my.test.testapp.interactor.RedditDetailInteractor
import com.my.test.testapp.navigation.RouterProvider
import com.my.test.testapp.ui.detail.RedditDetailPresenter
import com.my.test.testapp.ui.detail.RedditDetailRouter
import com.my.test.testapp.ui.detail.impl.RedditDetailPresenterImpl
import com.my.test.testapp.ui.detail.impl.RedditDetailRouterImpl
import com.my.test.testapp.ui.detail.util.NotificationProgressView
import com.my.test.testapp.ui.detail.util.NotificationProgressViewImpl
import com.my.test.testapp.ui.feed.RedditFeedRouter
import com.my.test.testapp.ui.feed.impl.RedditFeedRouterImpl
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
    internal fun provideDetailRouter(routerProvider: RouterProvider): RedditDetailRouter = RedditDetailRouterImpl(routerProvider)

    @Provides
    internal fun provideDetailPresenter(redditDetailRouter: RedditDetailRouter, redditDetailInteractor: RedditDetailInteractor): RedditDetailPresenter
            = RedditDetailPresenterImpl(redditDetailRouter, redditDetailInteractor)
}
