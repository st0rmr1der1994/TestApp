package com.my.test.testapp.di.module

import android.app.Activity
import com.my.test.testapp.di.scope.ActivityScope
import com.my.test.testapp.interactor.RedditFeedInteractor
import com.my.test.testapp.ui.feed.RedditFeedPresenter
import com.my.test.testapp.ui.feed.impl.RedditFeedPresenterImpl
import com.my.test.testapp.ui.feed.util.FixedCountLayoutManager
import com.my.test.testapp.utils.ITEMS_PER_PAGE_HORIZONTAL
import com.my.test.testapp.utils.ITEMS_PER_PAGE_VERTICAL
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class RedditFeedModule {

    @Provides
    @Named(FOR_LANDSCAPE)
    internal fun provideLandscapeLayoutManager(@ActivityScope activity: Activity): FixedCountLayoutManager
            = FixedCountLayoutManager(activity, ITEMS_PER_PAGE_HORIZONTAL)


    @Provides
    @Named(FOR_PORTRAIT)
    internal fun providePortraitLayoutManager(@ActivityScope activity: Activity): FixedCountLayoutManager
            = FixedCountLayoutManager(activity, ITEMS_PER_PAGE_VERTICAL)

    @Provides
    internal fun provideFeedPresenter(redditFeedInteractor: RedditFeedInteractor)
            : RedditFeedPresenter = RedditFeedPresenterImpl(redditFeedInteractor)
}
