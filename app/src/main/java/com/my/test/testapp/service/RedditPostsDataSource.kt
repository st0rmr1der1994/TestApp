package com.my.test.testapp.service

import com.my.test.testapp.entity.RedditPost
import io.reactivex.Observable

interface RedditPostsDataSource {

    fun redditPosts(): Observable<List<RedditPost>>
}
