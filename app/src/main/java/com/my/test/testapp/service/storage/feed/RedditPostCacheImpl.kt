package com.my.test.testapp.service.storage.feed

import com.my.test.testapp.entity.RedditPost
import io.reactivex.Observable
import io.realm.Realm
import io.realm.kotlin.where

class RedditPostCacheImpl : RedditPostCache {

    override fun savePosts(redditPosts: List<RedditPost>) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { it.copyToRealm(redditPosts) }
    }

    override fun fetchPosts(): Observable<List<RedditPost>> {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            Observable.fromArray(realm.where<RedditPost>().findAllAsync())
        }
        //TODO wrap this query
        return Observable.just(emptyList())
    }
}
