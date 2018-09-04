package com.my.test.testapp.ui.feed.impl

import com.my.test.testapp.entity.RedditPostModel
import com.my.test.testapp.interactor.RedditFeedInteractor
import com.my.test.testapp.ui.common.MvpPresenterImpl
import com.my.test.testapp.ui.feed.RedditFeedPresenter
import com.my.test.testapp.ui.feed.RedditFeedView
import io.reactivex.observers.DisposableObserver

class RedditFeedPresenterImpl(
        private val redditFeedInteractor: RedditFeedInteractor
) : MvpPresenterImpl<RedditFeedView>(), RedditFeedPresenter {

    override fun attachView(view: RedditFeedView) {
        super.attachView(view)
        redditFeedInteractor.interact(RedditFeedObserver(), Unit)
    }
}

private class RedditFeedObserver : DisposableObserver<List<RedditPostModel>>() {
    override fun onNext(t: List<RedditPostModel>) {

    }

    override fun onComplete() {
    }

    override fun onError(e: Throwable) {

    }
}
