package com.my.test.testapp.service.network.feed

import com.my.test.testapp.converter.RedditPostEntityToPostConverterImpl
import com.my.test.testapp.entity.RedditPost
import com.my.test.testapp.interactor.FeedMetadata
import com.my.test.testapp.service.RedditPostsDataSource
import com.my.test.testapp.service.storage.feed.RedditPostCache
import com.my.test.testapp.utils.ITEMS_PER_PAGE
import io.reactivex.Single

class RedditPostRemoteDataSource(
        private val redditFeedApi: RedditFeedApi,
        private val redditPostCache: RedditPostCache,
        private val converter: RedditPostEntityToPostConverterImpl
) : RedditPostsDataSource {

    private var nextCursor: String? = null

    override fun redditPosts(feedMetadata: FeedMetadata): Single<List<RedditPost>> {
        return if (feedMetadata.forceReload || nextCursor == null) {
            loadPostsInitial()
        } else {
            loadPostsAfter()
        }
    }

    private fun loadPostsInitial(): Single<List<RedditPost>> {
        return redditFeedApi.getTopPosts(ITEMS_PER_PAGE)
                .doOnSuccess { nextCursor = it.nextPageCursor }
                .map { converter.convert(it.data) }
                .doOnSuccess { redditPostCache.savePosts(it) }

    }

    private fun loadPostsAfter(): Single<List<RedditPost>> {
        return redditFeedApi.getTopPostsAfter(ITEMS_PER_PAGE, nextCursor!!)
                .doOnSuccess { nextCursor = it.nextPageCursor }
                .map { converter.convert(it.data) }
                .doOnSuccess { redditPostCache.savePosts(it) }
    }
}
