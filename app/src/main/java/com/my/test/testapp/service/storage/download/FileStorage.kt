package com.my.test.testapp.service.storage.download

import android.graphics.Bitmap
import io.reactivex.Single
import okhttp3.ResponseBody

interface FileStorage {

    fun saveToStorage(responseBody: ResponseBody): Single<String>

    fun saveToStorage(bitmap: Bitmap): Single<String>
}
