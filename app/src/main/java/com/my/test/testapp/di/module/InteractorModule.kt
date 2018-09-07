package com.my.test.testapp.di.module

import com.my.test.testapp.interactor.RedditDetailInteractor
import com.my.test.testapp.interactor.RedditFeedInteractor
import com.my.test.testapp.model.RedditRepository
import com.my.test.testapp.service.network.download.DownloadService
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {
    @Provides
    internal fun provideFeedInteractor(redditRepository: RedditRepository): RedditFeedInteractor
            = RedditFeedInteractor(redditRepository)

    @Provides
    internal fun provideDetailInteractor(downloadService: DownloadService): RedditDetailInteractor
            = RedditDetailInteractor(downloadService)
}
