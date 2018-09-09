package com.my.test.testapp.service

import com.my.test.testapp.entity.RedditPost
import com.my.test.testapp.interactor.FeedMetadata
import io.reactivex.Single

interface RedditPostsDataSource {

    fun redditPosts(feedMetadata: FeedMetadata): Single<List<RedditPost>>
}
