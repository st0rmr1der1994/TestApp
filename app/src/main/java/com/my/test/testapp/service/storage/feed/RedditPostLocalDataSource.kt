package com.my.test.testapp.service.storage.feed

import com.my.test.testapp.entity.RedditPost
import com.my.test.testapp.interactor.FeedMetadata
import com.my.test.testapp.service.RedditPostsDataSource
import io.reactivex.Flowable

class RedditPostLocalDataSource(private val redditPostCache: RedditPostCache) : RedditPostsDataSource {

    //TODO : rework this piece of junk with unused metadata
    override fun redditPosts(feedMetadata: FeedMetadata): Flowable<List<RedditPost>> = redditPostCache.fetchPosts().toFlowable()

}
