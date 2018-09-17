package com.my.test.testapp.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "posts",
        indices = [Index(value = ["name"], unique = false)])
data class RedditPost(
        val name: String,
        val postId: String,
        val pageCursor: String,
        val content: String,
        val author: String,
        val thumbnail: String?,
        val title: String?,
        val createdTimestamp: Double,
        val commentsCount: Int
) {
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0
}
