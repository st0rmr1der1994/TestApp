package com.my.test.testapp.ui.feed.impl

import com.bluelinelabs.conductor.Router
import com.my.test.testapp.entity.RedditPostModel
import com.my.test.testapp.navigation.RouterProvider
import com.my.test.testapp.navigation.go
import com.my.test.testapp.ui.detail.impl.RedditDetailViewImpl
import com.my.test.testapp.ui.feed.RedditFeedRouter

class RedditFeedRouterImpl(private val routerProvider: RouterProvider) : RedditFeedRouter {

    private val router: Router
        get() = routerProvider.router

    override fun goPostDetail(postModel: RedditPostModel) = router.go(RedditDetailViewImpl.create(postModel))
}
