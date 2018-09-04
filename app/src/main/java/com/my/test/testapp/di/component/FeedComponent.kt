package com.my.test.testapp.di.component

import com.my.test.testapp.di.module.PresenterModule
import com.my.test.testapp.ui.feed.impl.RedditFeedViewImpl
import dagger.Component

@Component(modules = [PresenterModule::class])
interface FeedComponent {

    fun inject(view: RedditFeedViewImpl)
}
