package com.my.test.testapp.repository

import com.my.test.testapp.BaseTest
import com.my.test.testapp.converter.RedditPostToPostModelConverterImpl
import com.my.test.testapp.entity.RedditPost
import com.my.test.testapp.entity.RedditPostModel
import com.my.test.testapp.interactor.FeedMetadata
import com.my.test.testapp.model.RedditRepository
import com.my.test.testapp.model.RedditRepositoryImpl
import com.my.test.testapp.service.DataSourceKind
import com.my.test.testapp.service.RedditDataSourceFactory
import com.my.test.testapp.service.RedditPostsDataSource
import com.my.test.testapp.service.storage.feed.RedditPostMemCache
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RedditFeedRepositoryTest : BaseTest() {

    @Mock
    lateinit var dataSourceFactory: RedditDataSourceFactory
    @Mock
    lateinit var converter: RedditPostToPostModelConverterImpl
    @Mock
    lateinit var dataSource: RedditPostsDataSource
    @Mock
    lateinit var memCache: RedditPostMemCache

    private var testSubscriber: TestSubscriber<List<RedditPostModel>>? = null
    private var redditRepository: RedditRepository? = null
    private var redditFeed: List<RedditPost>? = null

    @Before
    fun setUp() {
        testSubscriber = TestSubscriber()
        redditRepository = RedditRepositoryImpl(dataSourceFactory, converter, memCache)
        DataSourceKind.values().forEach { given(dataSourceFactory.getDataSource(it)).willReturn(dataSource) }
        val redditPost = Mockito.mock(RedditPost::class.java)
        redditFeed = listOf(redditPost)
    }

    @Test
    fun testNetworkLoadPosts() {
        val feedMetadata = FeedMetadata(true, true)
        prepareLoadPosts(feedMetadata, DataSourceKind.REMOTE)
        verify<RedditPostsDataSource>(dataSource).redditPosts(feedMetadata)
        verify<RedditPostMemCache>(memCache).isDirty = false
        verify<RedditPostMemCache>(memCache).savePosts(redditFeed!!, true)
    }

    @Test
    fun testOfflineFirstMemoryLoadPosts() {
        val feedMetadata = FeedMetadata(false, false)
        given(memCache.isEmpty).willReturn(false)
        prepareLoadPosts(feedMetadata, DataSourceKind.MEMORY)
        verify<RedditPostsDataSource>(dataSource).redditPosts(feedMetadata)
    }

    @Test
    fun testOfflineFirstLocalLoadPosts() {
        val feedMetadata = FeedMetadata(false, false)
        given(memCache.isEmpty).willReturn(true)
        prepareLoadPosts(feedMetadata, DataSourceKind.LOCAL)
        verify<RedditPostsDataSource>(dataSource, atLeast(1)).redditPosts(feedMetadata)
    }

    private fun prepareLoadPosts(feedMetadata: FeedMetadata, dataSourceKind: DataSourceKind) {
        given(dataSource.redditPosts(feedMetadata)).willReturn(Flowable.just(redditFeed))

        redditRepository!!.redditPosts(feedMetadata).subscribe(testSubscriber)

        verify<RedditDataSourceFactory>(dataSourceFactory).getDataSource(dataSourceKind)
    }
}
