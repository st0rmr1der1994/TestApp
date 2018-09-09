package com.my.test.testapp.model

import com.my.test.testapp.converter.RedditPostToPostModelConverterImpl
import com.my.test.testapp.entity.RedditPost
import com.my.test.testapp.entity.RedditPostModel
import com.my.test.testapp.interactor.FeedMetadata
import com.my.test.testapp.service.DataSourceKind
import com.my.test.testapp.service.RedditDataSourceFactory
import io.reactivex.Single

class RedditRepositoryImpl(
        private val dataSourceFactory: RedditDataSourceFactory,
        private val converter: RedditPostToPostModelConverterImpl
) : RedditRepository {

    override fun redditPosts(metadata: FeedMetadata): Single<List<RedditPostModel>> {
        //TODO : I think this metadata params should be reworked somehow
        return if (metadata.forceReload || metadata.paginatedRequest) {
            loadFromRemote(metadata)
                    .map { converter.convert(it) }
        } else {
            loadFromLocal(metadata)
                    .flatMap {
                        return@flatMap if (it.isEmpty()) {
                            loadFromRemote(metadata)
                        } else {
                            Single.just(it)
                        }
                    }
                    .map { converter.convert(it) }
        }
    }

    private fun loadFromLocal(metadata: FeedMetadata): Single<List<RedditPost>>
            = dataSourceFactory.getDataSource(DataSourceKind.LOCAL).redditPosts(metadata)

    private fun loadFromRemote(metadata: FeedMetadata): Single<List<RedditPost>>
            = dataSourceFactory.getDataSource(DataSourceKind.REMOTE).redditPosts(metadata)

}
