package com.example.todolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activity_table")
data class ActivityModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val Title: String,
    val Description: String,
    val CreatedDate: String,
    var Done: Boolean = false
)
