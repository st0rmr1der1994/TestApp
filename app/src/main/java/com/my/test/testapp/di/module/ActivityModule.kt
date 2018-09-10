package com.my.test.testapp.di.module

import android.app.Activity
import com.my.test.testapp.di.component.DetailComponent
import com.my.test.testapp.di.component.FeedComponent
import com.my.test.testapp.di.scope.ActivityScope
import com.my.test.testapp.navigation.RouterProvider
import dagger.Module
import dagger.Provides

@Module(subcomponents = [FeedComponent::class, DetailComponent::class])
class ActivityModule(private val activity: Activity) {

    @Provides
    @ActivityScope
    internal fun provideActivity(): Activity = activity

    @Provides
    @ActivityScope
    internal fun provideRouter(activity: Activity) = activity as RouterProvider

}
