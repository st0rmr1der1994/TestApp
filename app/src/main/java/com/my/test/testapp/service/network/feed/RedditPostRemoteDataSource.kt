package com.my.test.testapp.service.network.feed

import com.my.test.testapp.converter.RedditPostEntityToPostConverterImpl
import com.my.test.testapp.entity.RedditPost
import com.my.test.testapp.interactor.FeedMetadata
import com.my.test.testapp.service.RedditPostsDataSource
import com.my.test.testapp.service.storage.feed.RedditPostCache
import com.my.test.testapp.utils.ITEMS_PER_PAGE
import io.reactivex.Flowable

class RedditPostRemoteDataSource(
        private val redditFeedApi: RedditFeedApi,
        private val redditPostCache: RedditPostCache,
        private val converter: RedditPostEntityToPostConverterImpl
) : RedditPostsDataSource {

    private var nextCursor: String? = null

    override fun redditPosts(feedMetadata: FeedMetadata): Flowable<List<RedditPost>> {
        return if (feedMetadata.forceReload) {
            loadPostsInitial()
        } else {
            loadPostsAfter()
        }
    }

    private fun loadPostsInitial() = loadPosts(null)

    private fun loadPostsAfter() = loadPosts(nextCursor)

    private fun loadPosts(cursor: String?): Flowable<List<RedditPost>> {
        return redditFeedApi.getTopPosts(ITEMS_PER_PAGE, cursor)
                .map {
                    nextCursor = it.nextPageCursor
                    return@map converter.convert(it.data)
                }
                .doOnNext { redditPostCache.savePosts(it) }
    }
}
