package com.my.test.testapp.interactor

import com.my.test.testapp.service.network.download.DownloadService
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class RedditDetailInteractor(private val downloadService: DownloadService) : Interactor<String, String>() {
    override fun interact(metadata: String): Flowable<String> {
        return downloadService.downloadFileByUrl(metadata)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
