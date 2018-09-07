package com.my.test.testapp.ui.feed

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.my.test.testapp.entity.RedditPostModel

interface RedditFeedView : MvpView {

    fun showLoading()

    fun hideLoading()

    fun showError()

    fun hideError()

    fun showLoadedPosts(posts: List<RedditPostModel>)
}
