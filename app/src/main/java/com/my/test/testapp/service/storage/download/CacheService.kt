package com.my.test.testapp.service.storage.download

import android.graphics.Bitmap
import io.reactivex.Single

interface CacheService {

    fun fetchImageFromCache(url: String): Single<Bitmap>
}
