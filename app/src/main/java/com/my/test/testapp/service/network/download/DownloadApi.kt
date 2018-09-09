package com.my.test.testapp.service.network.download

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface DownloadApi {

    @Streaming
    @GET
    fun getPostContent(@Url fileUrl: String): Single<ResponseBody>
}
