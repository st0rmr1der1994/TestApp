package com.my.test.testapp.repository

import com.my.test.testapp.BaseTest
import com.my.test.testapp.converter.RedditPostToPostModelConverterImpl
import com.my.test.testapp.entity.RedditPost
import com.my.test.testapp.interactor.FeedMetadata
import com.my.test.testapp.model.RedditRepository
import com.my.test.testapp.model.RedditRepositoryImpl
import com.my.test.testapp.service.DataSourceKind
import com.my.test.testapp.service.RedditDataSourceFactory
import com.my.test.testapp.service.RedditPostsDataSource
import io.reactivex.Flowable
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

    private var redditRepository: RedditRepository? = null

    @Mock
    lateinit var dataSourceFactory: RedditDataSourceFactory
    @Mock
    lateinit var converter: RedditPostToPostModelConverterImpl
    @Mock
    lateinit var dataSource: RedditPostsDataSource

    @Before
    fun setUp() {
        redditRepository = RedditRepositoryImpl(dataSourceFactory, converter)
        DataSourceKind.values().forEach { given(dataSourceFactory.getDataSource(it)).willReturn(dataSource) }
    }

    @Test
    fun testNetworkLoadPosts() {
        val feedMetadata = FeedMetadata(true, true)
        prepareLoadPosts(feedMetadata, DataSourceKind.REMOTE)
        verify<RedditPostsDataSource>(dataSource).redditPosts(feedMetadata)
    }

    @Test
    fun testOfflineFirstLoadPosts() {
        val feedMetadata = FeedMetadata(false, false)
        prepareLoadPosts(feedMetadata, DataSourceKind.LOCAL)
        verify<RedditPostsDataSource>(dataSource, atLeast(1)).redditPosts(feedMetadata)
    }

    private fun prepareLoadPosts(feedMetadata: FeedMetadata, dataSourceKind: DataSourceKind) {
        val redditPost = Mockito.mock(RedditPost::class.java)
        val redditFeed = listOf(redditPost)
        given(dataSource.redditPosts(feedMetadata)).willReturn(Flowable.just(redditFeed))

        redditRepository?.redditPosts(feedMetadata)

        verify<RedditDataSourceFactory>(dataSourceFactory).getDataSource(dataSourceKind)
    }
}
