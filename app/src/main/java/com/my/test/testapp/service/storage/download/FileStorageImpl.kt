package com.my.test.testapp.service.storage.download

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import com.my.test.testapp.utils.ContentResolverProvider
import com.my.test.testapp.utils.FileStorageDirProvider
import io.reactivex.Single
import okhttp3.ResponseBody
import java.io.*

class FileStorageImpl(
        private val storageDirProvider: FileStorageDirProvider,
        private val contentResolverProvider: ContentResolverProvider) : FileStorage {
    override fun saveToStorage(responseBody: ResponseBody): Single<String> {
        return Single.create { emitter ->
            try {
                val downloadDir = File(storageDirProvider.provideDownloadDirPath())
                if (!downloadDir.exists()) {
                    downloadDir.mkdirs()
                }
                val fileName = System.currentTimeMillis().toString() + ".jpg"
                val downloadedFile = File(downloadDir, fileName)

                var inputStream: InputStream? = null
                var outputStream: OutputStream? = null

                try {
                    inputStream = responseBody.byteStream()
                    outputStream = FileOutputStream(downloadedFile)

                    val bufferedInputStream = BufferedInputStream(inputStream)
                    val bmp = BitmapFactory.decodeStream(bufferedInputStream)
                    bmp.compress(Bitmap.CompressFormat.JPEG,100, outputStream)
                    val result = MediaStore.Images.Media.insertImage(contentResolverProvider.getContentResolver(), bmp, fileName, "")
                    bmp.recycle()
                    result?.let {
                        emitter.onSuccess(it)
                    } ?: emitter.onError(IllegalStateException("Image failed to be stored in media"))
                } catch (e: IOException) {
                    emitter.onError(e)
                } finally {
                    inputStream?.close()
                    outputStream?.close()
                }
            } catch (e: IOException) {
                emitter.onError(e)
            }
        }
    }
}
