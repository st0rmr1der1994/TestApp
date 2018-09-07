package com.my.test.testapp.service.storage.feed

import com.my.test.testapp.entity.RedditPost
import io.reactivex.Observable
import io.realm.Realm
import io.realm.kotlin.where

class RedditPostCacheImpl : RedditPostCache {

    override fun savePosts(redditPosts: List<RedditPost>) {
        Realm.getDefaultInstance().use {
            it.beginTransaction()
            it.copyToRealm(redditPosts)
            it.commitTransaction()
        }
    }

    override fun fetchPosts(): Observable<List<RedditPost>> {

        return Observable.create { emitter ->
            val posts: MutableList<RedditPost> = ArrayList()
            Realm.getDefaultInstance().use {
                it.beginTransaction()
                posts.addAll(it.where<RedditPost>().findAll())
                it.commitTransaction()
                emitter.onNext(posts)
                emitter.onComplete()
            }
        }
    }
}
