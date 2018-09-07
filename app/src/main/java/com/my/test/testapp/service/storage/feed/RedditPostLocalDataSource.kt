package com.my.test.testapp.service.storage.feed

import com.my.test.testapp.entity.RedditPost
import com.my.test.testapp.service.RedditPostsDataSource
import io.reactivex.Observable

class RedditPostLocalDataSource(private val redditPostCache: RedditPostCache) : RedditPostsDataSource {

    override fun redditPosts(): Observable<List<RedditPost>> = redditPostCache.fetchPosts()

}
