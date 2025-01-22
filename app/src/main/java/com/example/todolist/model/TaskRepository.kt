package com.example.todolist.model

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class TaskRepository(
    private val taskDao: TaskDao,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun addTask(task: TaskModel) {
        withContext(defaultDispatcher) {
            taskDao.addTask(task)
        }
    }

    fun readAlLData(): LiveData<List<TaskModel>> {
        return taskDao.readAllData()
    }

    suspend fun deleteTask(task: TaskModel) {
        withContext(defaultDispatcher) {
            taskDao.deleteTask(task)
        }
    }

    suspend fun updateTask(task: TaskModel) {
        withContext(defaultDispatcher) {
            taskDao.updateTask(task)
        }
    }

}
