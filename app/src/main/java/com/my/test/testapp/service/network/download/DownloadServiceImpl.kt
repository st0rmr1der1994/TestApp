package com.my.test.testapp.service.network.download

import com.my.test.testapp.service.storage.download.FileStorage
import io.reactivex.Observable
import okhttp3.ResponseBody

class DownloadServiceImpl(
        private val downloadApi: DownloadApi,
        private val fileStorage: FileStorage
) : DownloadService {

    override fun downloadFileByUrl(url: String): Observable<String> {
        return downloadApi.getPostContent(url)
                .flatMap { storeDownloadedFile(it) }
    }

    private fun storeDownloadedFile(responseBody: ResponseBody): Observable<String> {
        return fileStorage.saveToStorage(responseBody)
    }
}
