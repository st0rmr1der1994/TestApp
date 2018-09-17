package com.my.test.testapp.service.storage.feed

import com.my.test.testapp.entity.RedditPost
import io.reactivex.Flowable

class RedditPostMemCacheImpl : RedditPostMemCache {

    private val cachedItems: MutableList<RedditPost> = ArrayList()

    override var isEmpty: Boolean = cachedItems.isEmpty()

    override var isDirty: Boolean = false

    override fun savePosts(posts: List<RedditPost>, forceReload: Boolean) {
        if (forceReload) {
            cachedItems.clear()
        }
        cachedItems.addAll(posts)
    }

    override fun fetchPosts(): Flowable<List<RedditPost>> = Flowable.just(cachedItems)
}
