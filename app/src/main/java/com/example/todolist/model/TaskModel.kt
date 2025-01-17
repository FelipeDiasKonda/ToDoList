package com.example.todolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "activity_table")
data class TaskModel(
    @PrimaryKey val id: UUID,
    val title: String,
    val description: String,
    val created_date: String,
    var done: Boolean,
)
