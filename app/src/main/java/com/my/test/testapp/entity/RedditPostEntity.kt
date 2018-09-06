package com.my.test.testapp.entity

import com.google.gson.annotations.SerializedName

data class RedditPostEntity(
        @SerializedName("id") val postId: String,
        @SerializedName("thumbnail") val postThumbnail: String,
        @SerializedName("author") val postAuthor: String,
        @SerializedName("title") val postTitle: String?
)
