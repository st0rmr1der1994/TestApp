package com.my.test.testapp.converter

import com.my.test.testapp.entity.RedditPost
import com.my.test.testapp.entity.RedditPostModel

interface RedditPostConverter {

    fun convert(redditPost: RedditPost): RedditPostModel

    fun convert(redditPosts: List<RedditPost>): List<RedditPostModel>
}
