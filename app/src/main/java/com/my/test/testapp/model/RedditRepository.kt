package com.my.test.testapp.model

import com.my.test.testapp.entity.RedditPostModel
import io.reactivex.Observable

interface RedditRepository {

    fun redditPosts(): Observable<List<RedditPostModel>>

    fun redditPost(postId: String): Observable<RedditPostModel>
}
