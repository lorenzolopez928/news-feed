package com.reign.mobilenews.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.reign.mobilenews.data.dao.NewsDao
import com.reign.mobilenews.data.dao.PageKeyDao
import com.reign.mobilenews.data.entities.NewsEntity
import com.reign.mobilenews.data.entities.PageKeyEntity

@Database(entities = [NewsEntity::class, PageKeyEntity::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun pageKeyDao(): PageKeyDao
}