package com.my.test.testapp.service

interface RedditDataSourceFactory {

    fun getDataSource(dataSourceKind: DataSourceKind): RedditPostsDataSource
}

enum class DataSourceKind { REMOTE, LOCAL }
