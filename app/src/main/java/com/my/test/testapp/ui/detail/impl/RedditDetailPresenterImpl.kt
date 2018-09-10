package com.my.test.testapp.ui.detail.impl

import com.my.test.testapp.interactor.RedditDetailInteractor
import com.my.test.testapp.ui.common.MvpPresenterImpl
import com.my.test.testapp.ui.detail.RedditDetailPresenter
import com.my.test.testapp.ui.detail.RedditDetailRouter
import com.my.test.testapp.ui.detail.RedditDetailView
import io.reactivex.observers.DisposableSingleObserver

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
            redditDetailInteractor.interact(RedditDetailObserver(view), url)
        }
    }

    override fun goBack() = redditDetailRouter.goBack()
}

private class RedditDetailObserver(private val view: RedditDetailView) : DisposableSingleObserver<String>() {
    override fun onStart() {
        view.showLoading()
    }

    override fun onSuccess(result: String) {
        view.hideLoading()
        view.showResult(result)
    }

    override fun onError(e: Throwable) {
        view.hideLoading()
    }
}
