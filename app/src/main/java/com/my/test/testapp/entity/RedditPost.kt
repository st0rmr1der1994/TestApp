package com.my.test.testapp.entity

import io.realm.RealmObject

open class RedditPost(
        var postId: String = "",
        var content: String = "",
        var thumbnail: String = "",
        var author: String = "",
        var title: String? = null
) : RealmObject()
