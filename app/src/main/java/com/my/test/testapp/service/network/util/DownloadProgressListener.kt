package com.my.test.testapp.service.network.util

interface DownloadProgressListener : DownloadProgressHolder {

    fun progress(bytesRead: Long, contentLength: Long, done: Boolean)
}
