package com.my.test.testapp.interactor

import com.my.test.testapp.BaseTest
import com.my.test.testapp.entity.RedditPostModel
import com.my.test.testapp.model.RedditRepository
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FeedInteractorTest : BaseTest() {

    private var feedInteractor: RedditFeedInteractor? = null
    private var posts: List<RedditPostModel>? = null

    @Mock
    lateinit var repository: RedditRepository
    @Mock
    lateinit var feedMetadata: FeedMetadata

    @Before
    fun setUp() {
        posts = listOf(Mockito.mock(RedditPostModel::class.java))
        feedInteractor = RedditFeedInteractor(repository)
        given(repository.redditPosts(feedMetadata)).willReturn(Flowable.just(posts))
    }

    @Test
    fun testLoadRedditFeed() {
        feedInteractor!!.interact(feedMetadata)
        verify<RedditRepository>(repository).redditPosts(feedMetadata)
        verifyNoMoreInteractions(repository)
    }

}
