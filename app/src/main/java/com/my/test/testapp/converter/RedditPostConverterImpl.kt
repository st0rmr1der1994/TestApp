package com.my.test.testapp.converter

import com.my.test.testapp.entity.RedditPost
import com.my.test.testapp.entity.RedditPostModel

class RedditPostConverterImpl : RedditPostConverter {
    override fun convert(redditPost: RedditPost): RedditPostModel {
        return RedditPostModel(
                postThumbnail = redditPost.thumbnail,
                postTitle = redditPost.title,
                postAuthor = redditPost.author
        )
    }

    override fun convert(redditPosts: List<RedditPost>): List<RedditPostModel> {
        return redditPosts.map { convert(it) }
    }

}
