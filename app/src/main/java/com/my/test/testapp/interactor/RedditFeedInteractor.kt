package com.my.test.testapp.interactor

import com.my.test.testapp.entity.RedditPostModel
import com.my.test.testapp.model.RedditRepository
import io.reactivex.Single

class RedditFeedInteractor(private val redditRepository: RedditRepository) : Interactor<List<RedditPostModel>, FeedMetadata>() {
    override fun interaction(metadata: FeedMetadata): Single<List<RedditPostModel>> {
        return redditRepository.redditPosts(metadata)
    }

}
data class FeedMetadata(val pageSize: Int, val forceReload: Boolean, val paginatedRequest: Boolean)
