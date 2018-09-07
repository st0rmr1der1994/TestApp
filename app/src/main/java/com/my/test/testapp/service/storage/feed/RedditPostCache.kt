package com.my.test.testapp.service.storage.feed

import com.my.test.testapp.entity.RedditPost
import io.reactivex.Observable

interface RedditPostCache {

    fun savePosts(redditPosts: List<RedditPost>)

    fun fetchPosts(): Observable<List<RedditPost>>
}
