package com.my.test.testapp.presentation

import com.my.test.testapp.BaseTest
import com.my.test.testapp.interactor.RedditDetailInteractor
import com.my.test.testapp.service.network.download.DownloadService
import com.my.test.testapp.ui.detail.RedditDetailPresenter
import com.my.test.testapp.ui.detail.RedditDetailRouter
import com.my.test.testapp.ui.detail.RedditDetailView
import com.my.test.testapp.ui.detail.impl.RedditDetailPresenterImpl
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

const val TEST_URL = "test/url"
const val RESULT_PATH = "result/path"

@RunWith(MockitoJUnitRunner::class)
class DetailPresenterTest : BaseTest() {

    @Mock
    lateinit var view: RedditDetailView
    @Mock
    lateinit var router: RedditDetailRouter

    private var interactor: RedditDetailInteractor? = null
    private var presenter: RedditDetailPresenter? = null

    @Before
    fun setUp() {
        interactor = TestDetailInteractor()
        presenter = RedditDetailPresenterImpl(router, interactor!!)
        presenter?.attachView(view)
    }

    @Test
    fun testLoadContent() {
        presenter?.loadContent(TEST_URL)
        verify<RedditDetailView>(view).showLoading()
        verify<RedditDetailView>(view).hideLoading()
        verify<RedditDetailView>(view).showResult(RESULT_PATH)
    }
}

private class TestDetailInteractor : RedditDetailInteractor(Mockito.mock(DownloadService::class.java)) {

    override fun interaction(metadata: String): Flowable<String> {
        return Flowable.just(RESULT_PATH)
    }
}

