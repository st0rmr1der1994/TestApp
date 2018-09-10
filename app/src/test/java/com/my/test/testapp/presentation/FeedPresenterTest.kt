package com.my.test.testapp.presentation

import com.my.test.testapp.BaseTest
import com.my.test.testapp.entity.RedditPostModel
import com.my.test.testapp.interactor.FeedMetadata
import com.my.test.testapp.interactor.RedditFeedInteractor
import com.my.test.testapp.ui.feed.RedditFeedPresenter
import com.my.test.testapp.ui.feed.RedditFeedRouter
import com.my.test.testapp.ui.feed.RedditFeedView
import com.my.test.testapp.ui.feed.impl.RedditFeedPresenterImpl
import com.my.test.testapp.ui.feed.impl.RedditFeedSubscriber
import io.reactivex.Flowable
import io.reactivex.subscribers.DisposableSubscriber
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FeedPresenterTest : BaseTest() {

    private var presenter: RedditFeedPresenter? = null

    @Mock
    lateinit var view: RedditFeedView
    @Mock
    lateinit var router: RedditFeedRouter
    @Mock
    lateinit var interactor: RedditFeedInteractor

    private var posts: List<RedditPostModel>? = null
    private var subscriber: DisposableSubscriber<List<RedditPostModel>>? = null

    @Before
    fun setUp() {
        subscriber = RedditFeedSubscriber(view)
        posts = listOf(Mockito.mock(RedditPostModel::class.java))
        presenter = RedditFeedPresenterImpl(router, interactor)
        presenter?.attachView(view)
    }

    @Test
    fun testLoadPosts() {
        //TODO : find out why events are not delivered/ view is not invoked
        val feedMetadata = FeedMetadata(false, false)
        given(interactor.interaction(feedMetadata)).willReturn(Flowable.just(posts))
        presenter?.loadPosts()
        verify<RedditFeedView>(view).showLoading()
        verify<RedditFeedView>(view).hideLoading()
        verify<RedditFeedView>(view).showLoadedPosts(posts!!)
    }

    @Test
    fun testOpenDetail() {
        val postModel = Mockito.mock(RedditPostModel::class.java)
        presenter?.openPostDetail(postModel)
        verify<RedditFeedRouter>(router).goPostDetail(postModel)
    }
}
