package com.my.test.testapp.di.module

import com.my.test.testapp.converter.RedditPostConverter
import com.my.test.testapp.converter.RedditPostConverterImpl
import dagger.Module
import dagger.Provides

@Module
class ConverterModule {
    @Provides
    internal fun providePostConverter(): RedditPostConverter = RedditPostConverterImpl()
}
