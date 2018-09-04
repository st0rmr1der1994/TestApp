package com.my.test.testapp.di.module

import com.my.test.testapp.interactor.RedditDetailInteractor
import com.my.test.testapp.interactor.RedditFeedInteractor
import com.my.test.testapp.ui.detail.RedditDetailPresenter
import com.my.test.testapp.ui.detail.impl.RedditDetailPresenterImpl
import com.my.test.testapp.ui.feed.RedditFeedPresenter
import com.my.test.testapp.ui.feed.impl.RedditFeedPresenterImpl
import dagger.Module
import dagger.Provides

@Module(includes = [ApplicationModule::class])
class PresenterModule {

    @Provides
    internal fun provideFeedPresenter(redditFeedInteractor: RedditFeedInteractor)
            : RedditFeedPresenter = RedditFeedPresenterImpl(redditFeedInteractor)

    @Provides
    internal fun provideDetailPresenter(redditDetailInteractor: RedditDetailInteractor)
            : RedditDetailPresenter = RedditDetailPresenterImpl(redditDetailInteractor)
}
