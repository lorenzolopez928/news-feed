package com.reign.mobilenews.modules.news_feed.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.reign.mobilenews.data.entities.NewsEntity
import com.reign.mobilenews.repository.ApiClient

class NewsEntityPagingSource(private val apiClient: ApiClient) :
    PagingSource<Int, NewsEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsEntity> {
        val pageNumber = params.key ?: 0
        return try {
            val response = apiClient.listNews(pageNumber)
            val data = response.newsList

            val nextPageNumber: Int? = pageNumber + 1

            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NewsEntity>): Int = 0
}