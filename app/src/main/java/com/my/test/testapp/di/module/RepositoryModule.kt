package com.my.test.testapp.di.module

import com.my.test.testapp.converter.RedditPostToPostModelConverterImpl
import com.my.test.testapp.model.RedditRepository
import com.my.test.testapp.model.RedditRepositoryImpl
import com.my.test.testapp.service.RedditDataSourceFactory
import com.my.test.testapp.service.RedditDataSourceFactoryImpl
import com.my.test.testapp.service.RedditPostsDataSource
import com.my.test.testapp.service.storage.feed.RedditPostCache
import com.my.test.testapp.service.storage.feed.RedditPostMemCache
import dagger.Module
import dagger.Provides
import javax.inject.Named

const val REMOTE_DATASOURCE = "DataSource#remote"
const val LOCAL_DATASOURCE = "DataSource#local"
const val MEMORY_DATASOURCE = "DataSource#memory"

@Module
class RepositoryModule {

    @Provides
    internal fun provideDataSourceFactory(
            @Named(REMOTE_DATASOURCE) remoteDataSource: RedditPostsDataSource,
            @Named(LOCAL_DATASOURCE) localDataSource: RedditPostsDataSource,
            @Named(MEMORY_DATASOURCE) memoryDataSource: RedditPostsDataSource
    ): RedditDataSourceFactory = RedditDataSourceFactoryImpl(remoteDataSource, localDataSource, memoryDataSource)

    @Provides
    internal fun provideRepository(
            dataSourceFactory: RedditDataSourceFactory,
            converter: RedditPostToPostModelConverterImpl,
            memCache: RedditPostMemCache,
            locaCache: RedditPostCache
    ): RedditRepository = RedditRepositoryImpl(dataSourceFactory, converter, memCache, locaCache)
}
