package com.reign.mobilenews.di.data

import androidx.lifecycle.MediatorLiveData
import androidx.paging.PagedList
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSourceModule {

    @Provides
    @Singleton
    fun providesMediatorLiveData(): MediatorLiveData<Any> {
        return MediatorLiveData()
    }

    @Provides
    @Singleton
    fun providesPagedListConfig(): PagedList.Config {
        return PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(20)
            .setPageSize(20)
            .build()
    }
}