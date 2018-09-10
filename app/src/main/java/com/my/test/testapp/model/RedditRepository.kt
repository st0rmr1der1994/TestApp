package com.my.test.testapp.model

import com.my.test.testapp.entity.RedditPostModel
import com.my.test.testapp.interactor.FeedMetadata
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

interface RedditRepository {

    fun redditPosts(metadata: FeedMetadata): Flowable<List<RedditPostModel>>
}
