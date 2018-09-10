package com.my.test.testapp.service.network.util

import io.reactivex.Observable

interface DownloadProgressHolder {
    var progressObservable: Observable<Int>
}
