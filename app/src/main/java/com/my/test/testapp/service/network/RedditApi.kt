package com.my.test.testapp.service.network

import com.my.test.testapp.entity.RedditFeedResponse
import com.my.test.testapp.entity.RedditPost
import io.reactivex.Observable
import retrofit2.http.GET

interface RedditApi {
    @GET("r/dankmemes/top.json")
    fun getTopPosts(): Observable<RedditFeedResponse>

    @GET("r/dankmemes/top.json")
    fun getPost(postId: String): Observable<RedditPost>
}
