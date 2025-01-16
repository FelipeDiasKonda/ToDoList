package com.example.todolist.model

import androidx.lifecycle.LiveData


class TaskRepository(private val taskDao: TaskDao) {

    suspend fun addActivity(activity: TaskModel) {
        taskDao.addActivity(activity)
    }

    fun readAlLData(): LiveData<List<TaskModel>> {
        return taskDao.readAllData()
    }

    suspend fun deleteActivity(activity: TaskModel) {
        taskDao.deleteActivity(activity)
    }

    suspend fun updateActivity(activity: TaskModel) {
        taskDao.updateActivity(activity)
    }

    suspend fun deleteAllActivities() {
        taskDao.deleteAllActivities()
    }


}
