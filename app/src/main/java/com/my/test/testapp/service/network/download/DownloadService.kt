package com.my.test.testapp.service.network.download

import io.reactivex.Observable

interface DownloadService {

    fun downloadFileByUrl(url: String): Observable<String>
}
