package com.my.test.testapp.service

class RedditDataSourceFactoryImpl(
        private val remoteDataSource: RedditPostsDataSource,
        private val localDataSource: RedditPostsDataSource
) : RedditDataSourceFactory {

    override fun getDataSource(dataSourceKind: DataSourceKind): RedditPostsDataSource {
        return when(dataSourceKind) {
            DataSourceKind.REMOTE -> remoteDataSource
            DataSourceKind.LOCAL -> localDataSource
        }
    }
}
