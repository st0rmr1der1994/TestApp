package com.my.test.testapp.service.network.download

import io.reactivex.Flowable

interface DownloadService {

    fun downloadFileByUrl(url: String): Flowable<String>
}
