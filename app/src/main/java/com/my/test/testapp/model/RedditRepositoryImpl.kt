package com.my.test.testapp.model

import com.my.test.testapp.converter.RedditPostToPostModelConverterImpl
import com.my.test.testapp.entity.RedditPostModel
import com.my.test.testapp.interactor.FeedMetadata
import com.my.test.testapp.service.DataSourceKind
import com.my.test.testapp.service.RedditDataSourceFactory
import com.my.test.testapp.service.storage.feed.RedditPostCache
import com.my.test.testapp.service.storage.feed.RedditPostMemCache
import io.reactivex.Flowable

class RedditRepositoryImpl(
        private val dataSourceFactory: RedditDataSourceFactory,
        private val converter: RedditPostToPostModelConverterImpl,
        private val memCache: RedditPostMemCache,
        private val localCache: RedditPostCache
) : RedditRepository {

    override fun redditPosts(metadata: FeedMetadata): Flowable<List<RedditPostModel>> {
        memCache.isDirty = metadata.forceReload
        if (metadata.paginatedRequest) {
            return loadFromRemote(metadata)
        }
        return if (!memCache.isDirty && !memCache.isEmpty) {
            loadFromMemory(metadata)
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
                .doOnNext { memCache.savePosts(it, metadata.forceReload) }
                .doOnNext {
                    if (metadata.forceReload) {
                        localCache.delete()
                    }
                    localCache.savePosts(it)
                }
                .map { converter.convert(it) }
                .doOnComplete { memCache.isDirty = false }
    }

}
