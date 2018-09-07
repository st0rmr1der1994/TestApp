package com.my.test.testapp.service.network.feed

import com.my.test.testapp.entity.RedditPost
import com.my.test.testapp.service.RedditPostsDataSource
import com.my.test.testapp.service.storage.feed.RedditPostCache
import io.reactivex.Observable

class RedditPostRemoteDataSource(
        private val redditFeedApi: RedditFeedApi,
        private val redditPostCache: RedditPostCache
) : RedditPostsDataSource {

    override fun redditPosts(): Observable<List<RedditPost>> {
        return redditFeedApi.getTopPosts()
                //rework this piece of junk
                .map { response -> response.data.map { RedditPost(it.postId, it.postContent,
                        it.postThumbnail, it.postAuthor, it.postTitle) } }
                .doOnNext { redditPostCache.savePosts(it) }
    }
}
