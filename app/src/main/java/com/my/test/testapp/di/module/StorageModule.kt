package com.my.test.testapp.di.module

import android.content.Context
import com.my.test.testapp.service.RedditPostsDataSource
import com.my.test.testapp.service.storage.download.FileStorage
import com.my.test.testapp.utils.FileStorageDirProvider
import com.my.test.testapp.service.storage.download.FileStorageImpl
import com.my.test.testapp.service.storage.feed.RedditPostCache
import com.my.test.testapp.service.storage.feed.RedditPostCacheImpl
import com.my.test.testapp.service.storage.feed.RedditPostLocalDataSource
import com.my.test.testapp.utils.ContentResolverProvider
import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Named
import javax.inject.Singleton

@Module
class StorageModule {

    @Provides
    internal fun provideRealm(): Realm = Realm.getDefaultInstance()

    @Provides
    @Singleton
    internal fun provideRedditPostCache(): RedditPostCache = RedditPostCacheImpl()

    @Provides
    @Singleton
    @Named(LOCAL_DATASOURCE)
    internal fun provideLocalDataSource(redditPostCache: RedditPostCache): RedditPostsDataSource
            = RedditPostLocalDataSource(redditPostCache)

    @Provides
    @Singleton
    internal fun provideDirProvider(appContext: Context): FileStorageDirProvider = FileStorageDirProvider((appContext))

    @Provides
    @Singleton
    internal fun provideContentResolverProvider(appContext: Context): ContentResolverProvider = ContentResolverProvider(appContext)

    @Provides
    @Singleton
    internal fun provideFileStorage(storageDirProvider: FileStorageDirProvider, contentResolverProvider: ContentResolverProvider): FileStorage
            = FileStorageImpl(storageDirProvider, contentResolverProvider)
}
