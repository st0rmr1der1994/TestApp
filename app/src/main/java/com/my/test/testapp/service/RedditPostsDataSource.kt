package com.my.test.testapp.service

import com.my.test.testapp.entity.RedditPost
import com.my.test.testapp.interactor.FeedMetadata
import io.reactivex.Flowable

interface RedditPostsDataSource {

    fun redditPosts(feedMetadata: FeedMetadata): Flowable<List<RedditPost>>
}
