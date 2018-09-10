package com.my.test.testapp.ui.feed

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.my.test.testapp.entity.RedditPostModel

interface RedditFeedPresenter : MvpPresenter<RedditFeedView> {

    fun loadPosts()

    fun loadMorePosts()

    fun forceLoadPosts()

    fun openPostDetail(postModel: RedditPostModel)
}
