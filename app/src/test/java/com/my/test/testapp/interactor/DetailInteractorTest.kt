package com.my.test.testapp.interactor

import com.my.test.testapp.BaseTest
import com.my.test.testapp.service.network.download.DownloadService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

const val TEST_URL = "test/url"

@RunWith(MockitoJUnitRunner::class)
class DetailInteractorTest : BaseTest() {
    private var detailInteractor: RedditDetailInteractor? = null

    @Mock
    lateinit var downloadService: DownloadService

    @Before
    fun setUp() {
        detailInteractor = RedditDetailInteractor(downloadService)
    }

    @Test
    fun testLoadFile() {
        detailInteractor?.interaction(TEST_URL)
        Mockito.verify<DownloadService>(downloadService).downloadFileByUrl(TEST_URL)
        Mockito.verifyNoMoreInteractions(downloadService)
    }

}
