package com.my.test.testapp.converter

import com.my.test.testapp.entity.RedditPost
import com.my.test.testapp.entity.RedditPostModel

class RedditPostToPostModelConverterImpl : BaseConverter<RedditPost, RedditPostModel>() {

    override fun convert(source: RedditPost) = RedditPostModel(
                postContent = source.content,
                postThumbnail = source.thumbnail,
                postTitle = source.title,
                postAuthor = source.author
            )
}
