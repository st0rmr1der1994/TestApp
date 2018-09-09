package com.my.test.testapp.di.component

import com.my.test.testapp.di.module.RedditFeedModule
import com.my.test.testapp.ui.feed.impl.RedditFeedViewImpl
import dagger.Subcomponent

@Subcomponent(modules = [RedditFeedModule::class])
interface FeedComponent {

    fun inject(view: RedditFeedViewImpl)

    @Subcomponent.Builder
    interface Builder {

        fun feedModule(module: RedditFeedModule)

        fun build(): FeedComponent
    }
}
