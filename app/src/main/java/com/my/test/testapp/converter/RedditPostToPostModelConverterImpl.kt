package com.my.test.testapp.converter

import com.my.test.testapp.entity.RedditPost
import com.my.test.testapp.entity.RedditPostModel
import java.util.concurrent.TimeUnit

class RedditPostToPostModelConverterImpl : BaseConverter<RedditPost, RedditPostModel>() {

    override fun convert(source: RedditPost) = RedditPostModel(
                postContent = source.content,
                postThumbnail = source.thumbnail,
                postTitle = source.title,
                postAuthor = source.author,
                postDate = convertTimestampToHourOffset(source.createdTimestamp.toLong()),
                postCommentsCount = source.commentsCount
            )

    private fun convertTimestampToHourOffset(timestamp: Long): Int {
        val millisDiff = System.currentTimeMillis() / 1000 - timestamp
        return TimeUnit.SECONDS.toHours(millisDiff).toInt()
    }
}
