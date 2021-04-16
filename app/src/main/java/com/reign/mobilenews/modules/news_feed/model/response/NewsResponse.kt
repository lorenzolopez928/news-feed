package com.reign.mobilenews.modules.news_feed.model.response

import com.google.gson.annotations.SerializedName
import com.reign.mobilenews.data.entities.NewsEntity

data class NewsResponse(
    @SerializedName("hits")
    val newsList: List<NewsEntity>,
    val page: Int
)