package com.example.todolist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "task_table")
data class TaskModel(
    @PrimaryKey val id: UUID,
    val title: String,
    val description: String,
    @ColumnInfo(name = "created_date")
    val created_date: String,
    @ColumnInfo(name = "is_done")
    var done: Boolean,
)
