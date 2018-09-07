package com.my.test.testapp.service.network.util

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

private const val DOWNLOAD_IDENTIFIER_HEADER = "download-identifier"

class DownloadInterceptor(private val progressListener: DownloadProgressListener) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        val builder = originalResponse.newBuilder()

        val downloadIdentifier = originalResponse.request().header(DOWNLOAD_IDENTIFIER_HEADER)
        val isAStream = originalResponse.header("content-type", "").equals("application/octet-stream")
        val fileIdentifierIsSet = downloadIdentifier != null && !downloadIdentifier.isEmpty()

        if (isAStream && fileIdentifierIsSet) {
            originalResponse.body()?.let {
                builder.body(DownloadProgressResponseBody(it, progressListener)) }
        } else {
            builder.body(originalResponse.body())
        }

        return builder.build()
    }
}
