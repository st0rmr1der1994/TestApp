package com.my.test.testapp.di.module

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.my.test.testapp.converter.RedditPostEntityToPostConverterImpl
import com.my.test.testapp.entity.RedditFeedResponse
import com.my.test.testapp.service.RedditPostsDataSource
import com.my.test.testapp.service.network.download.DownloadApi
import com.my.test.testapp.service.network.download.DownloadService
import com.my.test.testapp.service.network.download.DownloadServiceImpl
import com.my.test.testapp.service.network.feed.RedditFeedApi
import com.my.test.testapp.service.network.feed.RedditPostRemoteDataSource
import com.my.test.testapp.service.network.util.*
import com.my.test.testapp.service.storage.download.CacheService
import com.my.test.testapp.service.storage.download.FileStorage
import com.my.test.testapp.service.storage.feed.RedditPostCache
import com.my.test.testapp.utils.BASE_URL
import com.my.test.testapp.utils.NetworkManagerImpl
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

const val REGULAR_CLIENT = "NetworkClient#regular"
const val DOWNLOAD_CLIENT = "NetworkClient#download"

@Module
class NetworkModule {

    @Provides
    internal fun provideNetworkManager(context: Context): NetworkManager = NetworkManagerImpl(context)

    @Provides
    internal fun provideCustpmFeedDeserializer(): FeedResponseDeserializer = FeedResponseDeserializer(Gson())

    @Provides
    internal fun provideGson(feedResponseDeserializer: FeedResponseDeserializer): Gson {
        return GsonBuilder()
                .registerTypeAdapter(object : TypeToken<RedditFeedResponse>() {}.type, feedResponseDeserializer)
                .create()
    }

    @Provides
    @Singleton
    @Named(REGULAR_CLIENT)
    internal fun provideRegularOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
    }

    @Provides
    @Singleton
    internal fun provideDownloadProgressListener(): DownloadProgressListener = DownloadProgressListenerImpl()

    @Provides
    @Singleton
    @Named(DOWNLOAD_CLIENT)
    internal fun provideDownloadOkHttpClient(downloadProgressListener: DownloadProgressListener): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val downloadProgressInterceptor = DownloadInterceptor(downloadProgressListener)
        return OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(downloadProgressInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
    }

    @Provides
    @Singleton
    @Named(REGULAR_CLIENT)
    internal fun provideRegularRetrofit(gson: Gson, @Named(REGULAR_CLIENT) client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
    }

    @Provides
    @Singleton
    @Named(DOWNLOAD_CLIENT)
    internal fun provideDownloadRetrofit(@Named(DOWNLOAD_CLIENT) client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
    }

    @Provides
    @Singleton
    internal fun provideRegularApi(@Named(REGULAR_CLIENT) retrofit: Retrofit): RedditFeedApi {
        return retrofit.create(RedditFeedApi::class.java)
    }

    @Provides
    @Singleton
    internal fun provideDownloadApi(@Named(DOWNLOAD_CLIENT) retrofit: Retrofit): DownloadApi {
        return retrofit.create(DownloadApi::class.java)
    }

    @Provides
    @Singleton
    @Named(REMOTE_DATASOURCE)
    internal fun provideRemoteDataSource(redditFeedApi: RedditFeedApi,
                                         redditPostCache: RedditPostCache,
                                         converter: RedditPostEntityToPostConverterImpl): RedditPostsDataSource
            = RedditPostRemoteDataSource(redditFeedApi, redditPostCache, converter)

    @Provides
    @Singleton
    internal fun provideDownloadService(networkManager: NetworkManager, downloadApi: DownloadApi,
                                        cacheService: CacheService, fileStorage: FileStorage): DownloadService
            = DownloadServiceImpl(networkManager, downloadApi, cacheService , fileStorage)
}
