package com.reign.mobilenews.modules.news_feed.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.reign.mobilenews.data.NewsDatabase
import com.reign.mobilenews.data.dao.NewsDao
import com.reign.mobilenews.data.dao.PageKeyDao
import com.reign.mobilenews.data.entities.NewsEntity
import com.reign.mobilenews.data.entities.PageKeyEntity
import com.reign.mobilenews.repository.ApiClient
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class PageKeyRemoteMediator(
    private val db: NewsDatabase,
    private val apiClient: ApiClient
) : RemoteMediator<Int, NewsEntity>() {
    private val newsDao: NewsDao = db.newsDao()
    private val pageKeyDao: PageKeyDao = db.pageKeyDao()

    override suspend fun initialize(): InitializeAction {
        // Require that remote REFRESH is launched on initial load and succeeds before launching
        // remote PREPEND / APPEND.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsEntity>
    ): MediatorResult {
        try {
            // Get the closest item from PagingState that we want to load data around.
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    val remoteKey: PageKeyEntity? = db.withTransaction {
                        if (lastItem?.id != null) {
                            pageKeyDao.getNextPageKey(lastItem.id)
                        } else null
                    }

                    if (remoteKey?.nextPage == 1000) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    remoteKey?.nextPage
                }
            }

            val items = apiClient.listNews(
                page = loadKey
            ).newsList

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    //newsDao.clearAll()
                    pageKeyDao.clearAll()
                }
                items.forEach {
                    pageKeyDao.insertOrReplace(
                        PageKeyEntity(
                            it.id,
                            if (loadKey != null) loadKey + 1 else 1
                        )
                    )
                    val temp = newsDao.getNewsById(it.id)
                    if (temp != null)
                        it.deleted = temp.deleted
                    newsDao.insertOrReplace(it)
                }
            }

            return MediatorResult.Success(endOfPaginationReached = items.isEmpty())
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}