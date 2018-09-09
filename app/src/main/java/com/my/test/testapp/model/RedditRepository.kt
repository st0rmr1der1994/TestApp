package com.my.test.testapp.model

import com.my.test.testapp.entity.RedditPostModel
import com.my.test.testapp.interactor.FeedMetadata
import io.reactivex.Observable
import io.reactivex.Single

interface RedditRepository {

    fun redditPosts(metadata: FeedMetadata): Single<List<RedditPostModel>>
}
