package com.my.test.testapp.service.storage.feed

import com.my.test.testapp.entity.RedditPost
import io.reactivex.Flowable

interface RedditPostMemCache {

    var isDirty: Boolean

    var isEmpty: Boolean

    fun savePosts(posts: List<RedditPost>, forceReload: Boolean)

    fun fetchPosts(): Flowable<List<RedditPost>>
}
