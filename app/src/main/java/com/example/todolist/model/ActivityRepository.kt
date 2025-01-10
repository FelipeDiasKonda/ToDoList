package com.example.todolist.model

import androidx.lifecycle.LiveData


class ActivityRepository(private val activityDao: ActivityDao) {

    suspend fun addActivity(activity : ActivityModel ){
        activityDao.addActivity(activity)
    }

    fun readAlLData(): LiveData<List<ActivityModel>>{
        return activityDao.readAllData()
    }

    suspend fun deleteActivity(activity: ActivityModel){
        activityDao.deleteActivity(activity)
    }

    suspend fun updateActivity(activity: ActivityModel){
        activityDao.updateActivity(activity)
    }


}
