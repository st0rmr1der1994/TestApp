package com.my.test.testapp.entity

data class RedditFeedResponse(
        val data: List<RedditPostEntity>,
        val nextPageCursor: String
)
