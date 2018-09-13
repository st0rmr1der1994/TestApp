package com.my.test.testapp.service.network.util

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class DownloadProgressListenerImpl : DownloadProgressListener {

    private var progressSubject: PublishSubject<Int> = PublishSubject.create()

    override var progressObservable: Observable<Int> = progressSubject

    override fun progress(bytesRead: Long, contentLength: Long, done: Boolean) {
        val progress = ((bytesRead.toDouble() / contentLength.toDouble()) * 100).toInt()
        progressSubject.onNext(progress)
    }
}
