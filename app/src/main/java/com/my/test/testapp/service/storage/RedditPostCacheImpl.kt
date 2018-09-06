package com.my.test.testapp.service.storage

import com.my.test.testapp.entity.RedditPost
import io.reactivex.Observable
import io.realm.Realm
import io.realm.kotlin.where

class RedditPostCacheImpl : RedditPostCache {

    override fun savePost(redditPost: RedditPost) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { it.copyToRealm(redditPost) }
    }

    override fun savePosts(redditPosts: List<RedditPost>) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { it.copyToRealm(redditPosts) }
    }

    override fun fetchPost(postId: String): Observable<RedditPost> {
        val realm = Realm.getDefaultInstance()
        return Observable.just(realm.where<RedditPost>().equalTo("postId", postId).findFirstAsync())
    }

    override fun fetchPosts(): Observable<List<RedditPost>> {
        val realm = Realm.getDefaultInstance()
        return Observable.fromArray(realm.where<RedditPost>().findAllAsync())
    }
}
