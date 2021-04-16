package com.reign.mobilenews.repository.services

import com.reign.mobilenews.modules.news_feed.model.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    @GET("/api/v1/search_by_date?query=mobile")
    suspend fun getNewsList(@Query("page") page: Int?): NewsResponse
}