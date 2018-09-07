package com.my.test.testapp.ui.detail

import com.hannesdorfmann.mosby3.mvp.MvpView

interface RedditDetailView : MvpView {

    fun showLoading()

    fun hideLoading()

    fun showResult(result: String)
}
