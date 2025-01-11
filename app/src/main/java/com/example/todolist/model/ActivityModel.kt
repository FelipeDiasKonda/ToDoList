package com.example.todolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.UUID

@Entity(tableName = "activity_table")
data class ActivityModel(
    val id: UUID,
    val Title: String,
    val Description: String,
    val CreatedDate: String,
    var Done: Boolean = false
) : Serializable
