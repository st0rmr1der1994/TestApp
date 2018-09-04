package com.my.test.testapp.service

interface RedditDataSourceFactory {

    fun getDataSource(isConnected: Boolean): RedditPostsDataSource
}
