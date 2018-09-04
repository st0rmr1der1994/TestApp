package com.my.test.testapp.model

import com.my.test.testapp.converter.RedditPostConverter
import com.my.test.testapp.entity.RedditPostModel
import com.my.test.testapp.service.RedditDataSourceFactory
import com.my.test.testapp.utils.NetworkManager
import io.reactivex.Observable

class RedditRepositoryImpl(
        private val networkManager: NetworkManager,
        private val dataSourceFactory: RedditDataSourceFactory,
        private val converter: RedditPostConverter
) : RedditRepository {

    private fun isConnected() = networkManager.isConnected()

    override fun redditPosts(): Observable<List<RedditPostModel>> {
        return dataSourceFactory.getDataSource(isConnected()).redditPosts().map { converter.convert(it) }
    }

    override fun redditPost(postId: String): Observable<RedditPostModel> {
        return dataSourceFactory.getDataSource(isConnected()).redditPost(postId).map { converter.convert(it) }
    }

}
