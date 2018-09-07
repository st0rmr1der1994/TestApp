package com.my.test.testapp.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class RedditPostModel (
        val postContent: String,
        val postThumbnail: String,
        val postAuthor: String,
        val postTitle: String?
) : Parcelable
