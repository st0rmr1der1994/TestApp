package com.my.test.testapp.interactor

import com.my.test.testapp.service.network.download.DownloadService
import io.reactivex.Single

class RedditDetailInteractor(private val downloadService: DownloadService) : Interactor<String, String>() {
    override fun interaction(metadata: String): Single<String> {
        return downloadService.downloadFileByUrl(metadata)
    }
}
