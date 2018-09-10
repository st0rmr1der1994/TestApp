package com.my.test.testapp.interactor

import com.my.test.testapp.entity.RedditPostModel
import com.my.test.testapp.model.RedditRepository
import io.reactivex.Flowable

open class RedditFeedInteractor(private val redditRepository: RedditRepository) : Interactor<List<RedditPostModel>, FeedMetadata>() {
    override fun interaction(metadata: FeedMetadata): Flowable<List<RedditPostModel>> {
        return redditRepository.redditPosts(metadata)
    }

}
data class FeedMetadata(val forceReload: Boolean, val paginatedRequest: Boolean)
