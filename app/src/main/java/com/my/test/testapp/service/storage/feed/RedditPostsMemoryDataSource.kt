package com.my.test.testapp.service.storage.feed

import com.my.test.testapp.interactor.FeedMetadata
import com.my.test.testapp.service.RedditPostsDataSource

class RedditPostsMemoryDataSource(private val memCache: RedditPostMemCache) : RedditPostsDataSource {
    override fun redditPosts(feedMetadata: FeedMetadata) = memCache.fetchPosts()
}
