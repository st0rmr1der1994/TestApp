package com.my.test.testapp.di.module

import com.my.test.testapp.service.RedditPostsDataSource
import com.my.test.testapp.service.storage.RedditPostCache
import com.my.test.testapp.service.storage.RedditPostCacheImpl
import com.my.test.testapp.service.storage.RedditPostLocalDataSource
import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Named

@Module
class StorageModule {

    @Provides
    internal fun provideRealm(): Realm = Realm.getDefaultInstance()

    @Provides
    internal fun provideRedditPostCache(realm: Realm): RedditPostCache = RedditPostCacheImpl(realm)

    @Provides
    @Named("LOCAL")
    internal fun provideLocalDataSource(redditPostCache: RedditPostCache): RedditPostsDataSource
            = RedditPostLocalDataSource(redditPostCache)
}
