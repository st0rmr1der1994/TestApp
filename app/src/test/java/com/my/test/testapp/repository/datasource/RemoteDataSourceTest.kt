package com.my.test.testapp.repository.datasource

import com.my.test.testapp.BaseTest
import com.my.test.testapp.converter.RedditPostEntityToPostConverterImpl
import com.my.test.testapp.entity.RedditFeedResponse
import com.my.test.testapp.entity.RedditPost
import com.my.test.testapp.entity.RedditPostEntity
import com.my.test.testapp.interactor.FeedMetadata
import com.my.test.testapp.service.RedditPostsDataSource
import com.my.test.testapp.service.network.feed.RedditFeedApi
import com.my.test.testapp.service.network.feed.RedditPostRemoteDataSource
import com.my.test.testapp.service.storage.feed.RedditPostCache
import com.my.test.testapp.utils.ITEMS_PER_PAGE
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

private const val CURSOR = "cursor"

@RunWith(MockitoJUnitRunner::class)
class RemoteDataSourceTest : BaseTest() {

    private var redditPostsDataSource: RedditPostsDataSource? = null
    private var testSubscriber: TestSubscriber<List<RedditPost>>? = null

    @Mock
    lateinit var redditApi: RedditFeedApi
    @Mock
    lateinit var cache: RedditPostCache
    @Mock
    lateinit var converter: RedditPostEntityToPostConverterImpl
    private lateinit var redditEntities: List<RedditPostEntity>
    private lateinit var redditPosts: List<RedditPost>

    @Before
    fun setUp() {
        testSubscriber = TestSubscriber()
        redditPostsDataSource = RedditPostRemoteDataSource(redditApi, cache, converter)
        redditEntities = listOf(Mockito.mock(RedditPostEntity::class.java))
        redditPosts = listOf(Mockito.mock(RedditPost::class.java))
        val apiResponse = RedditFeedResponse(redditEntities, CURSOR)
        given(redditApi.getTopPosts(ITEMS_PER_PAGE, CURSOR)).willReturn(Flowable.just(apiResponse))
        given(redditApi.getTopPosts(ITEMS_PER_PAGE, null)).willReturn(Flowable.just(apiResponse))
        given(converter.convert(apiResponse.data)).willReturn(redditPosts)
    }

    @Test
    fun testLoadInitialFromRemote() {
        val feedMetadata = FeedMetadata(true, false)
        prepareLoadFromRemote(feedMetadata)
        verify<RedditFeedApi>(redditApi).getTopPosts(ITEMS_PER_PAGE, null)
    }

    @Test
    fun testLoadMoreFromRemote() {
        val initialfeedMetadata = FeedMetadata(true, false)
        prepareLoadFromRemote(initialfeedMetadata)
        val feedMetadata = FeedMetadata(false, true)
        prepareLoadFromRemote(feedMetadata)
        verify<RedditFeedApi>(redditApi).getTopPosts(ITEMS_PER_PAGE, CURSOR)
    }

    private fun prepareLoadFromRemote(feedMetadata: FeedMetadata) {
        redditPostsDataSource!!.redditPosts(feedMetadata).subscribe(testSubscriber)
        verify<RedditPostEntityToPostConverterImpl>(converter).convert(redditEntities)
        verify<RedditPostCache>(cache).savePosts(redditPosts)
    }
}
