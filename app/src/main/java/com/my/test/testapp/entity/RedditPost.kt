package com.my.test.testapp.entity

import io.realm.RealmObject

open class RedditPost(
        private var postId: String = "",
        private var author: String = "",
        private var title: String? = null
) : RealmObject()
