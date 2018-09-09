package com.my.test.testapp.converter

import com.my.test.testapp.entity.RedditPost
import com.my.test.testapp.entity.RedditPostEntity

class RedditPostEntityToPostConverterImpl : BaseConverter<RedditPostEntity, RedditPost>() {

    override fun convert(source: RedditPostEntity) = RedditPost(
            name = source.postName,
            postId = source.postId,
            subredditId = source.subredditId,
            content = source.postContent,
            thumbnail =  source.postThumbnail,
            author = source.postAuthor,
            title = source.postTitle
        )
}
