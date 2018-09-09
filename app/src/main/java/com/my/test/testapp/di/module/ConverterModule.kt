package com.my.test.testapp.di.module

import com.my.test.testapp.converter.RedditPostEntityToPostConverterImpl
import com.my.test.testapp.converter.RedditPostToPostModelConverterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ConverterModule {

    @Provides
    @Singleton
    internal fun providePostEntityToPostConverter(): RedditPostEntityToPostConverterImpl
            = RedditPostEntityToPostConverterImpl()

    @Provides
    @Singleton
    internal fun providePostToPostModelConverter(): RedditPostToPostModelConverterImpl
            = RedditPostToPostModelConverterImpl()
}
