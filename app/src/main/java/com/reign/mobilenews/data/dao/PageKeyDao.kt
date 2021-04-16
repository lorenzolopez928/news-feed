package com.reign.mobilenews.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reign.mobilenews.data.entities.PageKeyEntity

@Dao
interface PageKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(pageKey: PageKeyEntity)

    @Query("SELECT * FROM PageKeyEntity WHERE id LIKE :id")
    fun getNextPageKey(id: Int): PageKeyEntity?

    @Query("DELETE FROM PageKeyEntity")
    suspend fun clearAll()
}