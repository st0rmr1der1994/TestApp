package com.my.test.testapp.service.network.download

import android.graphics.Bitmap
import com.my.test.testapp.service.network.util.NetworkManager
import com.my.test.testapp.service.storage.download.CacheService
import com.my.test.testapp.service.storage.download.FileStorage
import io.reactivex.Flowable
import io.reactivex.Single
import okhttp3.ResponseBody

class DownloadServiceImpl(
        private val networkManager: NetworkManager,
        private val downloadApi: DownloadApi,
        private val cacheService: CacheService,
        private val fileStorage: FileStorage
) : DownloadService {

    override fun downloadFileByUrl(url: String): Flowable<String> {
        return if (networkManager.isConnected()) {
            downloadFromApi(url)
        } else {
            downloadFromCache(url)
        }
    }

    private fun downloadFromApi(url: String): Flowable<String> {
        return downloadApi.getPostContent(url)
                .flatMap { storeDownloadedFile(it) }
                .toFlowable()
    }

    private fun downloadFromCache(url: String): Flowable<String> {
        return cacheService.fetchImageFromCache(url)
                .flatMap { storeDownloadedFile(it) }
                .toFlowable()
    }

    private fun storeDownloadedFile(bitmap: Bitmap): Single<String> {
        return fileStorage.saveToStorage(bitmap)
    }

    private fun storeDownloadedFile(responseBody: ResponseBody): Single<String> {
        return fileStorage.saveToStorage(responseBody)
    }
}
