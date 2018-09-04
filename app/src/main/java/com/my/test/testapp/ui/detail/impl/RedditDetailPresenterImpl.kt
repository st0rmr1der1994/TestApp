package com.my.test.testapp.ui.detail.impl

import com.my.test.testapp.interactor.RedditDetailInteractor
import com.my.test.testapp.ui.common.MvpPresenterImpl
import com.my.test.testapp.ui.detail.RedditDetailPresenter
import com.my.test.testapp.ui.detail.RedditDetailView

class RedditDetailPresenterImpl(private val redditDetailInteractor: RedditDetailInteractor)
    : MvpPresenterImpl<RedditDetailView>(), RedditDetailPresenter {
}
