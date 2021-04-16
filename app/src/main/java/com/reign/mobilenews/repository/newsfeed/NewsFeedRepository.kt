package com.reign.mobilenews.repository.newsfeed

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.reign.mobilenews.data.NewsDatabase
import com.reign.mobilenews.data.entities.NewsEntity
import com.reign.mobilenews.modules.news_feed.paging.PageKeyRemoteMediator
import com.reign.mobilenews.repository.ApiClient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsFeedRepository @Inject constructor(
    val apiClient: ApiClient,
    val newsDatabase: NewsDatabase
) {

    @ExperimentalPagingApi
    fun listNews(): Flow<PagingData<NewsEntity>> = Pager(
        config = PagingConfig(20),
        remoteMediator = PageKeyRemoteMediator(
            newsDatabase,
            apiClient
        )
    ) {
        newsDatabase.newsDao().listNews()
    }.flow

}