package com.my.test.testapp.service.network.feed

import com.my.test.testapp.converter.RedditPostEntityToPostConverterImpl
import com.my.test.testapp.entity.RedditPost
import com.my.test.testapp.interactor.FeedMetadata
import com.my.test.testapp.service.RedditPostsDataSource
import com.my.test.testapp.service.storage.feed.RedditPostCache
import io.reactivex.Single

class RedditPostRemoteDataSource(
        private val redditFeedApi: RedditFeedApi,
        private val redditPostCache: RedditPostCache,
        private val converter: RedditPostEntityToPostConverterImpl
) : RedditPostsDataSource {

    private var nextCursor: String? = null

    override fun redditPosts(feedMetadata: FeedMetadata): Single<List<RedditPost>> {
        return if (feedMetadata.forceReload || nextCursor == null) {
            loadPostsInitial(feedMetadata.pageSize)
        } else {
            loadPostsAfter(feedMetadata.pageSize)
        }
    }

    private fun loadPostsInitial(pageSize: Int): Single<List<RedditPost>> {
        return redditFeedApi.getTopPosts(pageSize)
                .doOnSuccess { nextCursor = it.nextPageCursor }
                .map { converter.convert(it.data) }
                .doOnSuccess { redditPostCache.savePosts(it) }

    }

    private fun loadPostsAfter(pageSize: Int): Single<List<RedditPost>> {
        return redditFeedApi.getTopPostsAfter(pageSize, nextCursor!!)
                .doOnSuccess { nextCursor = it.nextPageCursor }
                .map { converter.convert(it.data) }
                .doOnSuccess { redditPostCache.savePosts(it) }
    }
}
