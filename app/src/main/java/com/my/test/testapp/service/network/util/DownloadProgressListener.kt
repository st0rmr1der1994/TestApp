package com.my.test.testapp.service.network.util

interface DownloadProgressListener {

    fun progress(bytesRead: Long, contentLength: Long, done: Boolean)
}
