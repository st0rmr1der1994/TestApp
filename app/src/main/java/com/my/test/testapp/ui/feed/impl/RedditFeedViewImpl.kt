package com.my.test.testapp.ui.feed.impl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.my.test.testapp.R
import com.my.test.testapp.di.component.DaggerFeedComponent
import com.my.test.testapp.di.component.FeedComponent
import com.my.test.testapp.di.module.PresenterModule
import com.my.test.testapp.ui.common.PresentableDaggerController
import com.my.test.testapp.ui.feed.RedditFeedPresenter
import com.my.test.testapp.ui.feed.RedditFeedView
import javax.inject.Inject

class RedditFeedViewImpl : PresentableDaggerController<RedditFeedView, RedditFeedPresenter>(), RedditFeedView {

    @Inject
    internal lateinit var feedPresenter: RedditFeedPresenter
    internal lateinit var feedComponent: FeedComponent

    override val presenter: RedditFeedPresenter
        get() = feedPresenter

    override fun initializeInjector() {
        feedComponent = DaggerFeedComponent.builder()
                .presenterModule(PresenterModule())
                .build()
        feedComponent.inject(this)
    }

    override fun inflateView(layoutInflater: LayoutInflater, viewGroup: ViewGroup): View =
            layoutInflater.inflate(R.layout.activity_main, viewGroup, false)
}
