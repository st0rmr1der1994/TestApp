package com.my.test.testapp.di.module

import com.my.test.testapp.converter.RedditPostToPostModelConverterImpl
import com.my.test.testapp.model.RedditRepository
import com.my.test.testapp.model.RedditRepositoryImpl
import com.my.test.testapp.service.RedditDataSourceFactory
import com.my.test.testapp.service.RedditDataSourceFactoryImpl
import com.my.test.testapp.service.RedditPostsDataSource
import com.my.test.testapp.service.network.util.NetworkManager
import dagger.Module
import dagger.Provides
import javax.inject.Named

const val REMOTE_DATASOURCE = "DataSource#remote"
const val LOCAL_DATASOURCE = "DataSource#local"

@Module
class RepositoryModule {

    @Provides
    internal fun provideDataSourceFactory(
            @Named(REMOTE_DATASOURCE) remoteDataSource: RedditPostsDataSource,
            @Named(LOCAL_DATASOURCE) localDataSource: RedditPostsDataSource
    ): RedditDataSourceFactory = RedditDataSourceFactoryImpl(remoteDataSource, localDataSource)

    @Provides
    internal fun provideRepository(
            networkManager: NetworkManager,
            dataSourceFactory: RedditDataSourceFactory,
            converter: RedditPostToPostModelConverterImpl
    ): RedditRepository = RedditRepositoryImpl(networkManager, dataSourceFactory, converter)
}
