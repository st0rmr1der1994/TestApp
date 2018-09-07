package com.my.test.testapp.ui.detail.impl

import com.my.test.testapp.interactor.RedditDetailInteractor
import com.my.test.testapp.ui.common.MvpPresenterImpl
import com.my.test.testapp.ui.detail.RedditDetailPresenter
import com.my.test.testapp.ui.detail.RedditDetailView
import io.reactivex.observers.DisposableObserver

class RedditDetailPresenterImpl(private val redditDetailInteractor: RedditDetailInteractor)
    : MvpPresenterImpl<RedditDetailView>(), RedditDetailPresenter {


    override fun detachView() {
        super.detachView()
        redditDetailInteractor.dispose()
    }

    override fun loadContent(url: String) {
        redditDetailInteractor.interact(RedditDetailObserver(view), url)
    }
}

private class RedditDetailObserver(private val view: RedditDetailView) : DisposableObserver<String>() {
    override fun onStart() {
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
