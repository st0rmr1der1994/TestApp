package com.my.test.testapp.di.module

import android.content.Context
import com.my.test.testapp.MainApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [
    NetworkModule::class,
    StorageModule::class,
    RepositoryModule::class,
    InteractorModule::class,
    ConverterModule::class
])
class ApplicationModule(private val application: MainApplication) {

    @Provides
    @Singleton
    internal fun provideAppContext(): Context = application

}
