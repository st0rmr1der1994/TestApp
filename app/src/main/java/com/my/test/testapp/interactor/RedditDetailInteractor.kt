package com.my.test.testapp.interactor

import com.my.test.testapp.entity.RedditPost
import io.reactivex.Observable

class RedditDetailInteractor : Interactor<RedditPost, String>() {
    override fun interactingObservable(metadata: String): Observable<RedditPost> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
