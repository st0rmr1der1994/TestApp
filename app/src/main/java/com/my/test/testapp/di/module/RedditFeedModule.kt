package com.my.test.testapp.di.module

import com.my.test.testapp.interactor.RedditFeedInteractor
import com.my.test.testapp.ui.feed.RedditFeedPresenter
import com.my.test.testapp.ui.feed.impl.RedditFeedPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class RedditFeedModule {

    @Provides
    internal fun provideFeedPresenter(redditFeedInteractor: RedditFeedInteractor)
            : RedditFeedPresenter = RedditFeedPresenterImpl(redditFeedInteractor)
}
