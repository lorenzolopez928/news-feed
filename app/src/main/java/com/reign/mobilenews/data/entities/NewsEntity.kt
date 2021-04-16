package com.reign.mobilenews.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class NewsEntity(
    @PrimaryKey
    @SerializedName("story_id")
    var id: Int,

    var title: String?,

    @SerializedName("story_title")
    var storyTitle: String?,

    var author: String?,

    @SerializedName("created_at")
    var createdAt: String?,

    @SerializedName("created_at_i")
    var createdAtTime: Long?,

    @SerializedName("story_url")
    var storyUrl: String?,

    var deleted: Boolean = false
)