package com.my.test.testapp.ui.detail.impl

import com.bluelinelabs.conductor.Router
import com.my.test.testapp.navigation.RouterProvider
import com.my.test.testapp.navigation.goBack
import com.my.test.testapp.ui.detail.RedditDetailRouter

class RedditDetailRouterImpl(private val routerProvider: RouterProvider) : RedditDetailRouter {

    private val router: Router
        get() = routerProvider.router

    override fun goBack() = router.goBack()

}
