package com.my.test.testapp.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "posts",
        indices = [Index(value = ["subredditId"], unique = false)])
data class RedditPost(
        @PrimaryKey
        val name: String,
        val postId: String,
        val subredditId: String,
        val content: String,
        val author: String,
        val thumbnail: String?,
        val title: String?,
        val createdTimestamp: Double,
        val commentsCount: Int
)
