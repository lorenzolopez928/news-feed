package com.reign.mobilenews.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reign.mobilenews.data.entities.NewsEntity

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(news: NewsEntity)

    @Query("Select * from NewsEntity where deleted != 1 order by createdAtTime desc")
    fun listNews(): PagingSource<Int, NewsEntity>

    @Query("Select * from NewsEntity where id = :id")
    fun getNewsById(id: Int): NewsEntity?


    @Query("Update NewsEntity set deleted = 1 where id=:id ")
    suspend fun softDelete(id: Int)

    @Query("DELETE FROM NewsEntity")
    suspend fun clearAll()
}