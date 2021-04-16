package com.reign.mobilenews.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PageKeyEntity(
    @PrimaryKey val id: Int,
    val nextPage: Int?
)