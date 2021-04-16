package com.reign.mobilenews.modules.news_feed.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.reign.mobilenews.data.entities.NewsEntity
import com.reign.mobilenews.modules.common.utils.SingleLiveEvent
import com.reign.mobilenews.repository.newsfeed.NewsFeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
class MainActivityViewModel @Inject constructor(var repository: NewsFeedRepository) :
    ViewModel() {

    val connectivityLiveData = SingleLiveEvent<Boolean>()

    private lateinit var _newsListFlow: Flow<PagingData<NewsEntity>>
    val newsListFlow: Flow<PagingData<NewsEntity>>
        get() = _newsListFlow

    init {
        getAllNews()
    }

    private fun getAllNews() {
        viewModelScope.launch {
            _newsListFlow = repository.listNews().cachedIn(viewModelScope)
        }
    }

    fun connectionChange(connected: Boolean) {
        connectivityLiveData.postValue(connected)
    }

    suspend fun hideNews(newsEntity: NewsEntity?) {
        newsEntity?.let {
            repository.newsDatabase.newsDao().softDelete(it.id)
        }
    }

}