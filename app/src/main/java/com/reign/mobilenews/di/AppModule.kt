package com.reign.mobilenews.di

import android.content.Context
import com.reign.mobilenews.NewsFeedApp
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(application: NewsFeedApp): Context {
        return application.applicationContext
    }
}