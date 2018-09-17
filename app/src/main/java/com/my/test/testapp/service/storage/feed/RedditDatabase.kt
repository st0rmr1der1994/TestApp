package com.my.test.testapp.service.storage.feed

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.my.test.testapp.entity.RedditPost

@Database(
        entities = [RedditPost::class],
        version = 1,
        exportSchema = false
)
abstract class RedditDatabase : RoomDatabase() {
    abstract fun redditDao(): RedditPostCache

    companion object {
        fun create(context: Context): RedditPostCache {
            return Room.databaseBuilder(context, RedditDatabase::class.java, "reddit_db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .redditDao()
        }
    }
}
