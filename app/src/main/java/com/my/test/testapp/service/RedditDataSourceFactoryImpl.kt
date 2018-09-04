package com.my.test.testapp.service

class RedditDataSourceFactoryImpl(
        private val remoteDataSource: RedditPostsDataSource,
        private val localDataSource: RedditPostsDataSource
) : RedditDataSourceFactory {

    override fun getDataSource(isConnected: Boolean): RedditPostsDataSource {
        return if (isConnected) remoteDataSource else localDataSource
    }
}
