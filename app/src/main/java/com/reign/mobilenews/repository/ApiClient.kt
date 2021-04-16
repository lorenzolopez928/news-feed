package com.reign.mobilenews.repository

import com.reign.mobilenews.modules.news_feed.model.response.NewsResponse
import com.reign.mobilenews.repository.services.WebServices

class ApiClient(private var webServices: WebServices) {

    suspend fun listNews(
        page: Int?
    ): NewsResponse {
        return webServices.getNewsList(page)
    }

    fun getWebServices() = webServices
}