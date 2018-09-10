package com.my.test.testapp.service.network.util

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class DownloadInterceptor(private val progressListener: DownloadProgressListener) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        val builder = originalResponse.newBuilder()
        originalResponse.body()?.let { builder.body(DownloadProgressResponseBody(it, progressListener)) }

        return builder.build()
    }
}
