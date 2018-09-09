package com.my.test.testapp.service.network.download

import io.reactivex.Single

interface DownloadService {

    fun downloadFileByUrl(url: String): Single<String>
}
