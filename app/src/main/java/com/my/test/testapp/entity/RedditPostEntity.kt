package com.my.test.testapp.entity

import com.google.gson.annotations.SerializedName

data class RedditPostEntity(
        @SerializedName("id") val postId: String,
        @SerializedName("name") val postName: String,
        @SerializedName("subreddit_id") val subredditId: String,
        @SerializedName("thumbnail") val postThumbnail: String,
        @SerializedName("url") val postContent: String,
        @SerializedName("author") val postAuthor: String,
        @SerializedName("title") val postTitle: String?,
        @SerializedName("created") val postCreatedTimestamp: Double,
        @SerializedName("num_comments") val postCommentsCount: Int
)
