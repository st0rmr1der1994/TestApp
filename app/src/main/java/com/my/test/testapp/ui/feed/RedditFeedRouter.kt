package com.my.test.testapp.ui.feed

import com.my.test.testapp.entity.RedditPostModel

interface RedditFeedRouter {

    fun goPostDetail(postModel: RedditPostModel)
}
