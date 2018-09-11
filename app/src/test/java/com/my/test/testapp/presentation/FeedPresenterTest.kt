package com.my.test.testapp.presentation

import com.my.test.testapp.BaseTest
import com.my.test.testapp.entity.RedditPostModel
import com.my.test.testapp.interactor.FeedMetadata
import com.my.test.testapp.interactor.RedditFeedInteractor
import com.my.test.testapp.model.RedditRepository
import com.my.test.testapp.ui.feed.RedditFeedPresenter
import com.my.test.testapp.ui.feed.RedditFeedRouter
import com.my.test.testapp.ui.feed.RedditFeedView
import com.my.test.testapp.ui.feed.impl.RedditFeedPresenterImpl
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
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

    private var presenter: RedditFeedPresenter? = null
    private var interactor: RedditFeedInteractor? = null
    private var posts: List<RedditPostModel>? = null

    @Before
    fun setUp() {
        posts = listOf(Mockito.mock(RedditPostModel::class.java))
        interactor = TestFeedInteractor(posts!!)
        presenter = RedditFeedPresenterImpl(router, interactor!!)
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

private class TestFeedInteractor(private val stubPosts: List<RedditPostModel>) : RedditFeedInteractor(Mockito.mock(RedditRepository::class.java)) {

    override fun interaction(metadata: FeedMetadata): Flowable<List<RedditPostModel>> {
        return Flowable.just(stubPosts)
    }

    override fun interact(subscriber: DisposableSubscriber<List<RedditPostModel>>, metadata: FeedMetadata) {
        val observable = interaction((metadata))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        addDisposable(observable.subscribeWith(subscriber))
    }
}

