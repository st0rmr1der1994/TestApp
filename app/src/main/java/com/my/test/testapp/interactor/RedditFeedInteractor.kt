package com.my.test.testapp.interactor

import com.my.test.testapp.entity.RedditPostModel
import com.my.test.testapp.model.RedditRepository
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class RedditFeedInteractor(private val redditRepository: RedditRepository) : Interactor<List<RedditPostModel>, FeedMetadata>() {
    override fun interact(metadata: FeedMetadata): Flowable<List<RedditPostModel>> {
        return redditRepository.redditPosts(metadata)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}
data class FeedMetadata(val forceReload: Boolean, val paginatedRequest: Boolean)
