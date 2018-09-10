package com.my.test.testapp.model

import com.my.test.testapp.converter.RedditPostToPostModelConverterImpl
import com.my.test.testapp.entity.RedditPostModel
import com.my.test.testapp.interactor.FeedMetadata
import com.my.test.testapp.service.DataSourceKind
import com.my.test.testapp.service.RedditDataSourceFactory
import io.reactivex.Flowable

class RedditRepositoryImpl(
        private val dataSourceFactory: RedditDataSourceFactory,
        private val converter: RedditPostToPostModelConverterImpl
) : RedditRepository {
    private var cachedItems: MutableList<RedditPostModel>? = null
    private var cacheIsDirty: Boolean = false

    override fun redditPosts(metadata: FeedMetadata): Flowable<List<RedditPostModel>> {
        //TODO : I think this metadata params should be reworked somehow
        cacheIsDirty = metadata.forceReload
        if (cachedItems != null && !cacheIsDirty && !metadata.paginatedRequest) {
            return Flowable.fromIterable(cachedItems).toList().toFlowable()
        } else if (cachedItems == null) {
            cachedItems = ArrayList()
        }

        return if (cacheIsDirty || metadata.paginatedRequest) {
            loadFromRemote(metadata)
        } else {
            Flowable.concat(loadFromLocal(metadata), loadFromRemote(metadata))
                    .filter { !it.isEmpty() }
                    .firstOrError()
                    .toFlowable()
        }
    }

    private fun loadFromLocal(metadata: FeedMetadata): Flowable<List<RedditPostModel>> {
        return dataSourceFactory.getDataSource(DataSourceKind.LOCAL).redditPosts(metadata)
                .map { converter.convert(it) }
    }

    private fun loadFromRemote(metadata: FeedMetadata): Flowable<List<RedditPostModel>> {
        return dataSourceFactory.getDataSource(DataSourceKind.REMOTE).redditPosts(metadata)
                .map { converter.convert(it) }
                .doOnNext { cachedItems?.addAll(it) }
                .doOnComplete { cacheIsDirty = false }
    }

}
