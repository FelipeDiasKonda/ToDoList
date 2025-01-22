package com.example.todolist.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TaskModel::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun activityDao(): TaskDao

    companion object {
        @Volatile
        private var instance: TaskDatabase? = null

        operator fun invoke(context: Context): TaskDatabase = instance ?: synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,
                TaskDatabase::class.java,
                "task_table"
            ).build()
        }.also {
            instance = it
        }
    }
}