package com.my.test.testapp.ui.feed.impl

import com.my.test.testapp.entity.RedditPostModel
import com.my.test.testapp.interactor.FeedMetadata
import com.my.test.testapp.interactor.RedditFeedInteractor
import com.my.test.testapp.ui.common.MvpPresenterImpl
import com.my.test.testapp.ui.feed.RedditFeedPresenter
import com.my.test.testapp.ui.feed.RedditFeedRouter
import com.my.test.testapp.ui.feed.RedditFeedView
import io.reactivex.subscribers.DisposableSubscriber

class RedditFeedPresenterImpl(
        private val redditFeedRouter: RedditFeedRouter,
        private val redditFeedInteractor: RedditFeedInteractor
) : MvpPresenterImpl<RedditFeedView>(), RedditFeedPresenter {

    override fun detachView() {
        super.detachView()
        redditFeedInteractor.dispose()
    }

    override fun loadPosts() = load(false, false)

    override fun loadMorePosts() = load(false, true)

    override fun forceLoadPosts() = load(true, false)

    private fun load(forceReload: Boolean, paginatedReuest: Boolean) {
        redditFeedInteractor.interact(RedditFeedSubscriber(view), FeedMetadata(forceReload, paginatedReuest))
    }

    override fun openPostDetail(postModel: RedditPostModel) = redditFeedRouter.goPostDetail(postModel)
}

private class RedditFeedSubscriber(private val view: RedditFeedView) : DisposableSubscriber<List<RedditPostModel>>() {
    override fun onStart() {
        super.onStart()
        view.hideError()
        view.showLoading()
    }

    override fun onNext(result: List<RedditPostModel>) {
        view.showLoadedPosts(result)
    }

    override fun onComplete() {
        view.hideLoading()
    }

    override fun onError(e: Throwable) {
        view.hideLoading()
        view.showError()
    }
}
