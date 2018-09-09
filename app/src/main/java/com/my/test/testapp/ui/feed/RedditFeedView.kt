package com.my.test.testapp.ui.feed

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.my.test.testapp.entity.RedditPostModel

interface RedditFeedView : MvpView {

    var pageSize: Int

    fun showLoading()

    fun hideLoading()

    fun showError()

    fun hideError()

    fun showLoadedPosts(posts: List<RedditPostModel>)
}
