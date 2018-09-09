package com.my.test.testapp.di.component

import com.my.test.testapp.di.module.RedditDetailModule
import com.my.test.testapp.ui.detail.impl.RedditDetailViewImpl
import dagger.Subcomponent

@Subcomponent(modules = [RedditDetailModule::class])
interface DetailComponent {

    fun inject(view: RedditDetailViewImpl)

    @Subcomponent.Builder
    interface Builder {

        fun detailModule(module: RedditDetailModule)

        fun build(): DetailComponent
    }
}
