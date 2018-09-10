package com.my.test.testapp.ui.detail

import com.hannesdorfmann.mosby3.mvp.MvpPresenter

interface RedditDetailPresenter : MvpPresenter<RedditDetailView> {

    fun loadContent(url: String?)

    fun goBack()
}
