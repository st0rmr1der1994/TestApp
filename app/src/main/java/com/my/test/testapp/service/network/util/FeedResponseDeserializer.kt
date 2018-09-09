package com.my.test.testapp.service.network.util

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.my.test.testapp.entity.RedditFeedResponse
import com.my.test.testapp.entity.RedditPostEntity
import java.lang.reflect.Type

private const val NEXT_PAGE = "after"
private const val TOP_LEVEL_DATA = "data"
private const val POSTS = "children"
private const val POST_DATA = "data"

class FeedResponseDeserializer(private val gson: Gson) : JsonDeserializer<RedditFeedResponse> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): RedditFeedResponse {
        val responseJsonObject = json.asJsonObject
        return if (responseJsonObject.has(TOP_LEVEL_DATA)) {
            val feedJsonObject =  responseJsonObject.getAsJsonObject(TOP_LEVEL_DATA)
            val nextPageCursor = if (feedJsonObject.has(NEXT_PAGE)) {
                feedJsonObject.get(NEXT_PAGE).asString
            } else {
                ""
            }
            if (feedJsonObject.has(POSTS)) {
                val feedChildren = feedJsonObject.getAsJsonArray(POSTS)
                val redditFeedEntities = feedChildren.map {
                    val feedEntityObjectWrapper = it.asJsonObject
                    gson.fromJson(feedEntityObjectWrapper.get(POST_DATA), RedditPostEntity::class.java)
                }
                RedditFeedResponse((redditFeedEntities), nextPageCursor)
            } else {
                RedditFeedResponse(emptyList(), nextPageCursor)
            }
        } else {
            throw IllegalArgumentException("Feed response cannot have an empty data")
        }
    }
}
