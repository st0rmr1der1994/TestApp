package com.my.test.testapp.di.component

import com.my.test.testapp.di.module.PresenterModule
import com.my.test.testapp.ui.detail.impl.RedditDetailViewImpl
import dagger.Component

@Component(dependencies = [ApplicationComponent::class], modules = [PresenterModule::class])
interface DetailComponent {

    fun inject(view: RedditDetailViewImpl)
}
