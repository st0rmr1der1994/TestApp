package com.my.test.testapp.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.my.test.testapp.entity.RedditFeedResponse
import com.my.test.testapp.entity.RedditPostEntity
import com.my.test.testapp.service.RedditPostsDataSource
import com.my.test.testapp.service.network.FeedResponseDeserializer
import com.my.test.testapp.service.network.RedditApi
import com.my.test.testapp.service.network.RedditPostRemoteDataSource
import com.my.test.testapp.service.storage.RedditPostCache
import com.my.test.testapp.utils.BASE_URL
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
class NetworkModule {

    @Provides
    internal fun provideGson(): Gson {
        return GsonBuilder()
                .registerTypeAdapter(object : TypeToken<RedditFeedResponse>() {}.type, FeedResponseDeserializer())
                .create()
    }

    @Provides
    internal fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
    }

    @Provides
    internal fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
    }

    @Provides
    internal fun provideApi(retrofit: Retrofit): RedditApi {
        return retrofit.create(RedditApi::class.java)
    }

    @Provides
    @Named("REMOTE")
    internal fun provideRemoteDataSource(redditApi: RedditApi, redditPostCache: RedditPostCache): RedditPostsDataSource
            = RedditPostRemoteDataSource(redditApi, redditPostCache)
}
