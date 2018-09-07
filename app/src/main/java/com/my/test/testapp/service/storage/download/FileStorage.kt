package com.my.test.testapp.service.storage.download

import io.reactivex.Observable
import okhttp3.ResponseBody

interface FileStorage {

    fun saveToStorage(responseBody: ResponseBody): Observable<String>
}
