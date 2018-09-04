package com.my.test.testapp.service.network

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.my.test.testapp.entity.RedditFeedResponse
import com.my.test.testapp.entity.RedditPostEntity
import java.lang.reflect.Type

class FeedResponseDeserializer : JsonDeserializer<RedditFeedResponse> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): RedditFeedResponse {
        val responseJsonObject = json.asJsonObject
        return if (responseJsonObject.has("data")) {
            val feedJsonObject =  responseJsonObject.getAsJsonObject("data")
            if (feedJsonObject.has("children")) {
                val feedChildren = feedJsonObject.getAsJsonArray("children")
                val redditFeedEntities = feedChildren.map {
                    val feedEntityObjectWrapper = it.asJsonObject
                    Gson().fromJson(feedEntityObjectWrapper.get("data"), RedditPostEntity::class.java)
                }
                RedditFeedResponse((redditFeedEntities))
            } else {
                RedditFeedResponse(emptyList())
            }
        } else {
            RedditFeedResponse(emptyList())
        }
    }
}
