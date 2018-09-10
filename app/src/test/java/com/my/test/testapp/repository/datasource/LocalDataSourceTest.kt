package com.my.test.testapp.repository.datasource

import com.my.test.testapp.BaseTest
import com.my.test.testapp.entity.RedditPost
import com.my.test.testapp.interactor.FeedMetadata
import com.my.test.testapp.service.RedditPostsDataSource
import com.my.test.testapp.service.storage.feed.RedditPostCache
import com.my.test.testapp.service.storage.feed.RedditPostLocalDataSource
import io.reactivex.Maybe
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LocalDataSourceTest : BaseTest() {

    private var redditPostsDataSource: RedditPostsDataSource? = null
    private var testSubscriber: TestSubscriber<List<RedditPost>>? = null

    private lateinit var redditPosts: List<RedditPost>

    @Mock
    lateinit var cache: RedditPostCache

    @Before
    fun setUp() {
        redditPosts = listOf(Mockito.mock(RedditPost::class.java))
        redditPostsDataSource = RedditPostLocalDataSource(cache)
        testSubscriber = TestSubscriber()
        given(cache.fetchPosts()).willReturn(Maybe.just(redditPosts))
    }

    @Test
    fun testLoadFromLocal() {
        redditPostsDataSource!!.redditPosts(Mockito.mock(FeedMetadata::class.java)).subscribe(testSubscriber)
        verify<RedditPostCache>(cache).fetchPosts()
    }
}
