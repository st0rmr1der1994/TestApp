package com.my.test.testapp.model

import com.my.test.testapp.converter.RedditPostToPostModelConverterImpl
import com.my.test.testapp.entity.RedditPostModel
import com.my.test.testapp.interactor.FeedMetadata
import com.my.test.testapp.service.DataSourceKind
import com.my.test.testapp.service.RedditDataSourceFactory
import com.my.test.testapp.service.storage.feed.RedditPostMemCache
import io.reactivex.Flowable

class RedditRepositoryImpl(
        private val dataSourceFactory: RedditDataSourceFactory,
        private val converter: RedditPostToPostModelConverterImpl,
        private val memCache: RedditPostMemCache
) : RedditRepository {

    override fun redditPosts(metadata: FeedMetadata): Flowable<List<RedditPostModel>> {
        //TODO : I think this metadata params should be reworked somehow
        memCache.isDirty = metadata.forceReload
        if (!memCache.isDirty && !metadata.paginatedRequest && !memCache.isEmpty) {
            return loadFromMemory(metadata)
        }

        return if (memCache.isDirty || metadata.paginatedRequest) {
            loadFromRemote(metadata)
        } else {
            Flowable.concat(loadFromLocal(metadata), loadFromRemote(metadata))
                    .filter { !it.isEmpty() }
                    .firstOrError()
                    .toFlowable()
        }
    }

    private fun loadFromMemory(metadata: FeedMetadata): Flowable<List<RedditPostModel>> {
        return dataSourceFactory.getDataSource(DataSourceKind.MEMORY).redditPosts(metadata)
                .map { converter.convert(it) }
    }

    private fun loadFromLocal(metadata: FeedMetadata): Flowable<List<RedditPostModel>> {
        return dataSourceFactory.getDataSource(DataSourceKind.LOCAL).redditPosts(metadata)
                .map { converter.convert(it) }
    }

    private fun loadFromRemote(metadata: FeedMetadata): Flowable<List<RedditPostModel>> {
        return dataSourceFactory.getDataSource(DataSourceKind.REMOTE).redditPosts(metadata)
                .doOnNext { memCache.savePosts(it, metadata.paginatedRequest) }
                .map { converter.convert(it) }
                .doOnComplete { memCache.isDirty = false }
    }

}
