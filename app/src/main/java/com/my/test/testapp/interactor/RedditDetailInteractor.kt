package com.my.test.testapp.interactor

import com.my.test.testapp.service.network.download.DownloadService
import io.reactivex.Observable

class RedditDetailInteractor(private val downloadService: DownloadService) : Interactor<String, String>() {
    override fun interactingObservable(metadata: String): Observable<String> {
        return downloadService.downloadFileByUrl(metadata)
    }
}
