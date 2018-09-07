package com.my.test.testapp.di.component

import com.my.test.testapp.di.module.RedditDetailModule
import com.my.test.testapp.di.scope.ActivityScope
import com.my.test.testapp.ui.detail.impl.RedditDetailViewImpl
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [RedditDetailModule::class])
interface DetailComponent {

    fun inject(view: RedditDetailViewImpl)
}
