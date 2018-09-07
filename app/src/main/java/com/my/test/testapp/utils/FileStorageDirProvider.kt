package com.my.test.testapp.utils

import android.content.Context
import android.os.Environment.DIRECTORY_PICTURES
import java.io.File.separator

class FileStorageDirProvider(private val appContext: Context) {
    fun provideDownloadDirPath() = buildString {
        append(appContext.getExternalFilesDir(DIRECTORY_PICTURES).absolutePath)
        append(separator)
    }
}
