package com.my.test.testapp.interactor

import com.my.test.testapp.BaseTest
import com.my.test.testapp.model.RedditRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FeedInteractorTest : BaseTest() {

    private var feedInteractor: RedditFeedInteractor? = null

    @Mock
    lateinit var repository: RedditRepository

    @Before
    fun setUp() {
        feedInteractor = RedditFeedInteractor(repository)
    }

    @Test
    fun testLoadRedditFeed() {
        val feedMetadata: FeedMetadata = Mockito.mock(FeedMetadata::class.java)
        feedInteractor?.interaction(feedMetadata)
        verify<RedditRepository>(repository).redditPosts(feedMetadata)
        verifyNoMoreInteractions(repository)
    }

}
