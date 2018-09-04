package com.my.test.testapp.di.module

import com.my.test.testapp.converter.RedditPostConverter
import com.my.test.testapp.model.RedditRepository
import com.my.test.testapp.model.RedditRepositoryImpl
import com.my.test.testapp.service.RedditDataSourceFactory
import com.my.test.testapp.service.RedditDataSourceFactoryImpl
import com.my.test.testapp.service.RedditPostsDataSource
import com.my.test.testapp.utils.NetworkManager
import com.my.test.testapp.utils.NetworkManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class RepositoryModule {

    @Provides
    internal fun provideNetworkManager(): NetworkManager = NetworkManagerImpl()

    @Provides
    internal fun provideDataSourceFactory(
            @Named("REMOTE") remoteDataSource: RedditPostsDataSource,
            @Named("LOCAL") localDataSource: RedditPostsDataSource
    ): RedditDataSourceFactory = RedditDataSourceFactoryImpl(remoteDataSource, localDataSource)

    @Provides
    internal fun provideRepository(
            networkManager: NetworkManager,
            dataSourceFactory: RedditDataSourceFactory,
            postConverter: RedditPostConverter
    ): RedditRepository = RedditRepositoryImpl(networkManager, dataSourceFactory, postConverter)
}
