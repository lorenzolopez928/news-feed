package com.reign.mobilenews.di.data

import androidx.room.Room
import androidx.room.RoomDatabase
import com.reign.mobilenews.NewsFeedApp
import com.reign.mobilenews.data.NewsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {
    @Provides
    @Singleton
    fun getDatabase(application: NewsFeedApp): NewsDatabase {
        return Room.databaseBuilder(application, NewsDatabase::class.java, "news_feed_database.db")
            .fallbackToDestructiveMigration()
            .addCallback(RoomCallback())
            .build()
    }

    class RoomCallback : RoomDatabase.Callback() {
    }
}

