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
        redditFeedInteractor.interact(RedditFeedObserver(view), Unit)
    }

    override fun detachView() {
        super.detachView()
        redditFeedInteractor.dispose()
    }
}

private class RedditFeedObserver(private val view: RedditFeedView) : DisposableObserver<List<RedditPostModel>>() {
    override fun onStart() {
        view.hideLoading()
    }

    override fun onNext(posts: List<RedditPostModel>) {
        view.showLoadedPosts(posts)
    }

    override fun onComplete() {
        view.hideLoading()
    }

    override fun onError(e: Throwable) {
        view.hideLoading()
    }
}
