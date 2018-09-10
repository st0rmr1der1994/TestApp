package com.my.test.testapp.service

import android.graphics.Bitmap
import com.my.test.testapp.BaseTest
import com.my.test.testapp.service.network.download.DownloadApi
import com.my.test.testapp.service.network.download.DownloadService
import com.my.test.testapp.service.network.download.DownloadServiceImpl
import com.my.test.testapp.service.network.util.NetworkManager
import com.my.test.testapp.service.storage.download.CacheService
import com.my.test.testapp.service.storage.download.FileStorage
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.subscribers.TestSubscriber
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

const val TEST_URL = "test/url"

@RunWith(MockitoJUnitRunner::class)
class DownloadServiceTest : BaseTest() {

    private var downloadService: DownloadService? = null
    private var testSubscriber: TestSubscriber<String>? = null

    @Mock
    lateinit var networkManager: NetworkManager
    @Mock
    lateinit var downloadApi: DownloadApi
    @Mock
    lateinit var cacheService: CacheService
    @Mock
    lateinit var fileStorage: FileStorage

    @Before
    fun setUp() {
        testSubscriber = TestSubscriber()
        downloadService = DownloadServiceImpl(networkManager, downloadApi, cacheService, fileStorage)
    }

    @Test
    fun testDownloadFromRemote() {
        val responseBody = Mockito.mock(ResponseBody::class.java)
        given(networkManager.isConnected()).willReturn(true)
        given(downloadApi.getPostContent(TEST_URL)).willReturn(Single.just(responseBody))
        downloadService!!.downloadFileByUrl(TEST_URL).subscribe(testSubscriber)
        verify<DownloadApi>(downloadApi).getPostContent(TEST_URL)
        verify<FileStorage>(fileStorage).saveToStorage(responseBody)
    }

    @Test
    fun testDownloadFromCache() {
        val bitmap = Mockito.mock(Bitmap::class.java)
        given(networkManager.isConnected()).willReturn(false)
        given(cacheService.fetchImageFromCache(TEST_URL)).willReturn(Single.just(bitmap))
        downloadService!!.downloadFileByUrl(TEST_URL).subscribe(testSubscriber)
        verify<CacheService>(cacheService).fetchImageFromCache(TEST_URL)
        verify<FileStorage>(fileStorage).saveToStorage(bitmap)
    }

}
