package com.my.test.testapp.service.storage.download

import io.reactivex.Single
import okhttp3.ResponseBody

interface FileStorage {

    fun saveToStorage(responseBody: ResponseBody): Single<String>
}
