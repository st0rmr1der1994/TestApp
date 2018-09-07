package com.my.test.testapp.utils

import android.content.Context

class ContentResolverProvider(private val appContext: Context) {
    fun getContentResolver() = appContext.contentResolver
}
