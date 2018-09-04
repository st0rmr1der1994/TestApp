package com.my.test.testapp.di.module

import com.my.test.testapp.interactor.RedditDetailInteractor
import com.my.test.testapp.interactor.RedditFeedInteractor
import com.my.test.testapp.model.RedditRepository
import dagger.Module
import dagger.Provides

@Module(includes = [ApplicationModule::class])
class InteractorModule {
    @Provides
    internal fun provideFeedInteractor(redditRepository: RedditRepository): RedditFeedInteractor = RedditFeedInteractor(redditRepository)

    @Provides
    internal fun provideDetailInteractor(): RedditDetailInteractor = RedditDetailInteractor()
}
