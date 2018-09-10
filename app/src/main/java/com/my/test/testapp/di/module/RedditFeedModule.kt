package com.my.test.testapp.di.module

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import android.support.v7.widget.RecyclerView
import com.my.test.testapp.di.scope.ActivityScope
import com.my.test.testapp.interactor.RedditFeedInteractor
import com.my.test.testapp.navigation.RouterProvider
import com.my.test.testapp.ui.feed.RedditFeedPresenter
import com.my.test.testapp.ui.feed.RedditFeedRouter
import com.my.test.testapp.ui.feed.impl.RedditFeedPresenterImpl
import com.my.test.testapp.ui.feed.impl.RedditFeedRouterImpl
import dagger.Module
import dagger.Provides

@Module
class RedditFeedModule {

    @Provides
    internal fun provideLayoutManager(@ActivityScope activity: Activity): RecyclerView.LayoutManager
            = LinearLayoutManager(activity, VERTICAL, false)

    @Provides
    internal fun provideFeedRouter(routerProvider: RouterProvider): RedditFeedRouter = RedditFeedRouterImpl(routerProvider)

    @Provides
    internal fun provideFeedPresenter(redditFeedRouter: RedditFeedRouter, redditFeedInteractor: RedditFeedInteractor)
            : RedditFeedPresenter = RedditFeedPresenterImpl(redditFeedRouter, redditFeedInteractor)
}
