package com.my.test.testapp.service.storage

import com.my.test.testapp.entity.RedditPost
import com.my.test.testapp.service.RedditPostsDataSource
import io.reactivex.Observable

class RedditPostLocalDataSource(private val redditPostCache: RedditPostCache) : RedditPostsDataSource {

    override fun redditPosts(): Observable<List<RedditPost>> = redditPostCache.fetchPosts()

    override fun redditPost(postId: String): Observable<RedditPost> = redditPostCache.fetchPost(postId)

}
