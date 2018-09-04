package com.my.test.testapp.interactor

import com.my.test.testapp.entity.RedditPostModel
import com.my.test.testapp.model.RedditRepository
import io.reactivex.Observable

class RedditFeedInteractor(private val redditRepository: RedditRepository) : Interactor<List<RedditPostModel>, Unit>() {
    override fun interactingObservable(metadata: Unit): Observable<List<RedditPostModel>> {
        return redditRepository.redditPosts()
    }
}
