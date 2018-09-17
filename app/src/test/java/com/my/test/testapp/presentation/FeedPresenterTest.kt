package com.my.test.testapp.presentation

import com.my.test.testapp.BaseTest
import com.my.test.testapp.entity.RedditPostModel
import com.my.test.testapp.interactor.FeedMetadata
import com.my.test.testapp.interactor.RedditFeedInteractor
import com.my.test.testapp.ui.feed.RedditFeedPresenter
import com.my.test.testapp.ui.feed.RedditFeedRouter
import com.my.test.testapp.ui.feed.RedditFeedView
import com.my.test.testapp.ui.feed.impl.RedditFeedPresenterImpl
import io.reactivex.Flowable
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

    @Mock
    lateinit var view: RedditFeedView
    @Mock
    lateinit var router: RedditFeedRouter
    @Mock
    lateinit var interactor: RedditFeedInteractor

    private var presenter: RedditFeedPresenter? = null
    private var posts: List<RedditPostModel>? = null

    @Before
    fun setUp() {
        posts = listOf(Mockito.mock(RedditPostModel::class.java))
        val metadata = FeedMetadata(false, false)
        given(interactor.interact(metadata)).willReturn(Flowable.just(posts))
        presenter = RedditFeedPresenterImpl(router, interactor)
        presenter?.attachView(view)
    }

    @Test
    fun testLoadPosts() {
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

