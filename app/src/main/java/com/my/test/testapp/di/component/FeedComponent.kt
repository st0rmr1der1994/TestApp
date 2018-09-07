package com.my.test.testapp.di.component

import com.my.test.testapp.di.module.RedditFeedModule
import com.my.test.testapp.di.scope.ActivityScope
import com.my.test.testapp.ui.feed.impl.RedditFeedViewImpl
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [RedditFeedModule::class])
interface FeedComponent {

    fun inject(view: RedditFeedViewImpl)
}
