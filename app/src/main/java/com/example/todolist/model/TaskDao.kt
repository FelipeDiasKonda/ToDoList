package com.example.todolist.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addActivity(activityModel: TaskModel)

    @Query("SELECT * FROM activity_table ORDER by title ASC")
    fun readAllData(): LiveData<List<TaskModel>>

    @Update
    suspend fun updateActivity(activityModel: TaskModel)

    @Delete
    suspend fun deleteActivity(activityModel: TaskModel)

    @Query("DELETE FROM activity_table")
    suspend fun deleteAllActivities()

}
