package com.my.test.testapp.service.network.feed

import com.my.test.testapp.entity.RedditFeedResponse
import io.reactivex.Observable
import retrofit2.http.GET


interface RedditFeedApi {
    @GET("r/dankmemes/top.json")
    fun getTopPosts(): Observable<RedditFeedResponse>
}
