package com.my.test.testapp.ui.feed

import com.hannesdorfmann.mosby3.mvp.MvpPresenter

interface RedditFeedPresenter : MvpPresenter<RedditFeedView> {

    fun loadPosts()

    fun loadMorePosts()

    fun forceLoadPosts()
}
