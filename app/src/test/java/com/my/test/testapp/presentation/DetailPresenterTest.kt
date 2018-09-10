package com.my.test.testapp.presentation

import com.my.test.testapp.BaseTest
import com.my.test.testapp.entity.RedditPostModel
import com.my.test.testapp.interactor.RedditDetailInteractor
import com.my.test.testapp.ui.detail.RedditDetailPresenter
import com.my.test.testapp.ui.detail.RedditDetailRouter
import com.my.test.testapp.ui.detail.RedditDetailView
import com.my.test.testapp.ui.detail.impl.RedditDetailPresenterImpl
import com.my.test.testapp.ui.detail.impl.RedditDetailSubscriber
import io.reactivex.Flowable
import io.reactivex.subscribers.DisposableSubscriber
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

const val TEST_URL = "test/url"
const val RESULT_PATH = "result/path"

@RunWith(MockitoJUnitRunner::class)
class DetailPresenterTest : BaseTest() {

    private var presenter: RedditDetailPresenter? = null

    @Mock
    lateinit var view: RedditDetailView
    @Mock
    lateinit var router: RedditDetailRouter
    @Mock
    lateinit var interactor: RedditDetailInteractor

    private var posts: List<RedditPostModel>? = null
    private var subscriber: DisposableSubscriber<String>? = null

    @Before
    fun setUp() {
        subscriber = RedditDetailSubscriber(view)
        posts = listOf(Mockito.mock(RedditPostModel::class.java))
        presenter = RedditDetailPresenterImpl(router, interactor)
        presenter?.attachView(view)
    }

    @Test
    fun testLoadPosts() {
        //TODO : find out why events are not delivered/ view is not invoked
        given(interactor.interaction(TEST_URL)).willReturn(Flowable.just(RESULT_PATH))
        presenter?.loadContent(TEST_URL)
        verify<RedditDetailView>(view).showLoading()
        verify<RedditDetailView>(view).hideLoading()
        verify<RedditDetailView>(view).showResult(RESULT_PATH)
    }
}
