package com.my.test.testapp.di.module

import android.content.Context
import com.my.test.testapp.converter.RedditPostConverter
import com.my.test.testapp.model.RedditRepository
import com.my.test.testapp.model.RedditRepositoryImpl
import com.my.test.testapp.service.RedditDataSourceFactory
import com.my.test.testapp.service.RedditDataSourceFactoryImpl
import com.my.test.testapp.service.RedditPostsDataSource
import com.my.test.testapp.service.network.util.NetworkManager
import com.my.test.testapp.utils.NetworkManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named

const val REMOTE_DATASOURCE = "DataSource#remote"
const val LOCAL_DATASOURCE = "DataSource#local"

@Module
class RepositoryModule {

    @Provides
    internal fun provideNetworkManager(context: Context): NetworkManager = NetworkManagerImpl(context)

    @Provides
    internal fun provideDataSourceFactory(
            @Named(REMOTE_DATASOURCE) remoteDataSource: RedditPostsDataSource,
            @Named(LOCAL_DATASOURCE) localDataSource: RedditPostsDataSource
    ): RedditDataSourceFactory = RedditDataSourceFactoryImpl(remoteDataSource, localDataSource)

    @Provides
    internal fun provideRepository(
            networkManager: NetworkManager,
            dataSourceFactory: RedditDataSourceFactory,
            postConverter: RedditPostConverter
    ): RedditRepository = RedditRepositoryImpl(networkManager, dataSourceFactory, postConverter)
}
