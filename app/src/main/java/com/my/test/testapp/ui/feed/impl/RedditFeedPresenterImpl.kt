package com.my.test.testapp.ui.feed.impl

import com.my.test.testapp.entity.RedditPostModel
import com.my.test.testapp.interactor.FeedMetadata
import com.my.test.testapp.interactor.RedditFeedInteractor
import com.my.test.testapp.ui.common.MvpPresenterImpl
import com.my.test.testapp.ui.feed.RedditFeedPresenter
import com.my.test.testapp.ui.feed.RedditFeedRouter
import com.my.test.testapp.ui.feed.RedditFeedView
import io.reactivex.observers.DisposableSingleObserver

class RedditFeedPresenterImpl(
        private val redditFeedRouter: RedditFeedRouter,
        private val redditFeedInteractor: RedditFeedInteractor
) : MvpPresenterImpl<RedditFeedView>(), RedditFeedPresenter {

    override fun detachView() {
        super.detachView()
        redditFeedInteractor.dispose()
    }

    override fun loadPosts() = load(view.pageSize, false, false)

    override fun loadMorePosts() = load(view.pageSize, false, true)

    override fun forceLoadPosts() = load(view.pageSize, true, false)

    private fun load(pageSize: Int, forceReload: Boolean, paginatedReuest: Boolean) {
        redditFeedInteractor.interact(RedditFeedObserver(view), FeedMetadata(pageSize, forceReload, paginatedReuest))
    }

    override fun openPostDetail(postModel: RedditPostModel) = redditFeedRouter.goPostDetail(postModel)
}

private class RedditFeedObserver(private val view: RedditFeedView) : DisposableSingleObserver<List<RedditPostModel>>() {
    override fun onStart() {
        view.hideError()
        view.showLoading()
    }

    override fun onSuccess(result: List<RedditPostModel>) {
        view.hideLoading()
        view.showLoadedPosts(result)
    }

    override fun onError(e: Throwable) {
        view.hideLoading()
        view.showError()
    }
}
