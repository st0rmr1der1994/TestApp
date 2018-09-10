package com.my.test.testapp.service.storage.feed

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.my.test.testapp.entity.RedditPost
import io.reactivex.Maybe

@Dao
interface RedditPostCache {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePosts(posts : List<RedditPost>)

    @Query("SELECT * FROM posts")
    fun fetchPosts() : Maybe<List<RedditPost>>
}
