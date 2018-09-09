package com.my.test.testapp.service.storage.download

import android.content.Context
import android.graphics.Bitmap
import com.facebook.common.references.CloseableReference
import com.facebook.datasource.DataSource
import com.facebook.imagepipeline.core.ImagePipeline
import com.facebook.imagepipeline.datasource.BaseBitmapReferenceDataSubscriber
import com.facebook.imagepipeline.image.CloseableImage
import com.facebook.imagepipeline.request.ImageRequest
import io.reactivex.Single
import java.util.concurrent.Executors

class CacheServiceImpl(
        private val imagePipeline: ImagePipeline
) : CacheService {

    override fun fetchImageFromCache(url: String): Single<Bitmap> {
        return Single.create { emitter ->
            val imageRequest = ImageRequest.fromUri(url)
            val dataSource = imagePipeline.fetchImageFromBitmapCache(imageRequest, this)
            dataSource.subscribe(object : BaseBitmapReferenceDataSubscriber() {
                override fun onNewResultImpl(bitmapReference: CloseableReference<Bitmap>?) {
                    //TODO : find out why isInBitmapMemoryCache returtns true and this returns null
                    bitmapReference?.let { it -> emitter.onSuccess(it.get()) }
                            ?: emitter.onError(IllegalStateException("Bitmap is null for given url"))
                }

                override fun onFailureImpl(dataSource: DataSource<CloseableReference<CloseableImage>>) {
                    emitter.onError(IllegalStateException("Cannot get bitmap from cache"))
                }

            }, Executors.newSingleThreadExecutor())

        }
    }
}
