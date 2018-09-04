package com.my.test.testapp.service.storage

import com.my.test.testapp.entity.RedditPost
import io.reactivex.Observable

interface RedditPostCache {

    fun savePost(redditPost: RedditPost)

    fun savePosts(redditPosts: List<RedditPost>)

    fun fetchPost(postId: String): Observable<RedditPost>

    fun fetchPosts(): Observable<List<RedditPost>>
}
