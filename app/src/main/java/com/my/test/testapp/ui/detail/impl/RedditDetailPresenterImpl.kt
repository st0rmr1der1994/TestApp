package com.my.test.testapp.ui.detail.impl

import com.my.test.testapp.interactor.RedditDetailInteractor
import com.my.test.testapp.ui.common.MvpPresenterImpl
import com.my.test.testapp.ui.detail.RedditDetailPresenter
import com.my.test.testapp.ui.detail.RedditDetailRouter
import com.my.test.testapp.ui.detail.RedditDetailView
import io.reactivex.subscribers.DisposableSubscriber

class RedditDetailPresenterImpl(
        private val redditDetailRouter: RedditDetailRouter,
        private val redditDetailInteractor: RedditDetailInteractor
)
    : MvpPresenterImpl<RedditDetailView>(), RedditDetailPresenter {

    override fun detachView() {
        super.detachView()
        redditDetailInteractor.dispose()
    }

    override fun loadContent(url: String?) {
        url?.let {
            redditDetailInteractor.interact(RedditDetailSubscriber(view), url)
        }
    }

    override fun goBack() = redditDetailRouter.goBack()
}

private class RedditDetailSubscriber(private val view: RedditDetailView) : DisposableSubscriber<String>() {
    override fun onStart() {
        super.onStart()
        view.showLoading()
    }

    override fun onNext(result: String) {
        view.showResult(result)
    }

    override fun onComplete() {
        view.hideLoading()
    }

    override fun onError(e: Throwable) {
        view.hideLoading()
    }
}
