package com.my.test.testapp.service.storage

import com.my.test.testapp.entity.RedditPost
import io.reactivex.Observable
import io.realm.Realm
import io.realm.kotlin.where

class RedditPostCacheImpl(private val realm: Realm) : RedditPostCache {

    override fun savePost(redditPost: RedditPost) {
        realm.beginTransaction()
        realm.copyToRealm(redditPost)
        realm.commitTransaction()
    }

    override fun savePosts(redditPosts: List<RedditPost>) {
        realm.beginTransaction()
        realm.copyToRealm(redditPosts)
        realm.commitTransaction()
    }

    override fun fetchPost(postId: String): Observable<RedditPost> {
        return Observable.just(realm.where<RedditPost>().equalTo("postId", postId).findFirst())
    }

    override fun fetchPosts(): Observable<List<RedditPost>> {
        return Observable.just(realm.where<RedditPost>().findAll())
    }
}
