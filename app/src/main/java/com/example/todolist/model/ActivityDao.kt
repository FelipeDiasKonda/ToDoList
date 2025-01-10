package com.example.todolist.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addActivity(activityModel: ActivityModel)

    @Query("SELECT * FROM activity_table ORDER by Title ASC")
    fun readAllData(): LiveData<List<ActivityModel>>

    @Update
    suspend fun updateActivity(activityModel: ActivityModel)

    @Delete
    suspend fun deleteActivity(activityModel: ActivityModel)

}
