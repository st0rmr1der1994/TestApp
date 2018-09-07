package com.my.test.testapp.service.network

import com.my.test.testapp.entity.RedditPost
import com.my.test.testapp.service.RedditPostsDataSource
import com.my.test.testapp.service.storage.RedditPostCache
import io.reactivex.Observable

class RedditPostRemoteDataSource(
        private val redditApi: RedditApi,
        private val redditPostCache: RedditPostCache
) : RedditPostsDataSource {

    override fun redditPosts(): Observable<List<RedditPost>> {
        return redditApi.getTopPosts()
                //rework this piece of junk
                .map { response -> response.data.map { RedditPost(it.postId, it.postThumbnail,
                        it.postContent, it.postAuthor, it.postTitle) } }
                .doOnNext { redditPostCache.savePosts(it) }
    }

    override fun redditPost(postId: String): Observable<RedditPost> {
        return redditApi.getPost(postId).doOnNext { redditPostCache.savePost(it) }
    }
}
