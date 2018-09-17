package com.my.test.testapp.di.component

import android.app.Activity
import com.my.test.testapp.di.module.ActivityModule
import com.my.test.testapp.di.scope.ActivityScope
import dagger.Component


@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun activity(): Activity

    fun feedComponentBuilder(): FeedComponent.Builder

    fun detailComponentBuilder(): DetailComponent.Builder
}
