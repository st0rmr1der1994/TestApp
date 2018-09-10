package com.my.test.testapp.service.network.feed

import com.my.test.testapp.entity.RedditFeedResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditFeedApi {

    @GET("r/dankmemes/top.json")
    fun getTopPosts(@Query("limit") limit: Int, @Query("after") cursor: String?): Flowable<RedditFeedResponse>
}
