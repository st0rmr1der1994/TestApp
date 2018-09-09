package com.my.test.testapp.di.module

import android.content.Context
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipeline
import com.my.test.testapp.service.RedditPostsDataSource
import com.my.test.testapp.service.storage.download.CacheService
import com.my.test.testapp.service.storage.download.CacheServiceImpl
import com.my.test.testapp.service.storage.download.FileStorage
import com.my.test.testapp.service.storage.download.FileStorageImpl
import com.my.test.testapp.service.storage.feed.RedditDatabase
import com.my.test.testapp.service.storage.feed.RedditPostCache
import com.my.test.testapp.service.storage.feed.RedditPostLocalDataSource
import com.my.test.testapp.utils.ContentResolverProvider
import com.my.test.testapp.utils.FileStorageDirProvider
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class StorageModule {

    @Provides
    @Singleton
    internal fun provideRedditPostCache(appContext: Context): RedditPostCache = RedditDatabase.create(appContext)

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
    internal fun provideImagePipeline(): ImagePipeline = Fresco.getImagePipeline()

    @Provides
    @Singleton
    internal fun provideCacheService(imagePipeline: ImagePipeline): CacheService = CacheServiceImpl(imagePipeline)

    @Provides
    @Singleton
    internal fun provideContentResolverProvider(appContext: Context): ContentResolverProvider = ContentResolverProvider(appContext)

    @Provides
    @Singleton
    internal fun provideFileStorage(storageDirProvider: FileStorageDirProvider, contentResolverProvider: ContentResolverProvider): FileStorage
            = FileStorageImpl(storageDirProvider, contentResolverProvider)
}
