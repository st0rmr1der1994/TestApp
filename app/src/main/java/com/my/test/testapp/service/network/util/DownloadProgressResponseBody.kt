package com.my.test.testapp.service.network.util

import okhttp3.ResponseBody
import okio.*


class DownloadProgressResponseBody(
        private val responseBody: ResponseBody,
        private val progressListener: DownloadProgressListener
) : ResponseBody() {

    private var bufferedSource: BufferedSource? = null

    override fun contentLength() = responseBody.contentLength()

    override fun contentType() = responseBody.contentType()

    override fun source() = bufferedSource?.let {
        Okio.buffer(getProgressSourceWrapper(responseBody.source(), responseBody.contentLength()))
    } ?: bufferedSource

    private fun getProgressSourceWrapper(source: Source, contentLength: Long)
            = ProgressSourceWrapper(source, contentLength, progressListener)
}

private class ProgressSourceWrapper(
        delegate: Source,
        private val contentLength: Long,
        private val progressListener: DownloadProgressListener
) : ForwardingSource(delegate) {
    private var totalBytesRead = 0L

    override fun read(sink: Buffer, byteCount: Long): Long {
        val bytesRead = super.read(sink, byteCount)
        totalBytesRead += if (bytesRead != -1L) bytesRead else 0
        progressListener.progress(totalBytesRead, contentLength, bytesRead == -1L)
        return bytesRead
    }
}
