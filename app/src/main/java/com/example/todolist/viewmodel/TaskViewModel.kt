package com.example.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.model.TaskDatabase
import com.example.todolist.model.TaskModel
import com.example.todolist.model.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository
    val allActivities: LiveData<List<TaskModel>>

    init {
        val dao = TaskDatabase.getDatabase(application).activityDao()
        repository = TaskRepository(dao)
        allActivities = repository.readAlLData()
    }


    fun completeActivity(activity: TaskModel) {
        activity.done = true
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.updateActivity(activity)
            }
        }
    }


    fun undoActivity(activity: TaskModel) {
        activity.done = false
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.updateActivity(activity)
            }
        }
    }

    fun deleteActivity(activity: TaskModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteActivity(activity)
            }
        }
    }


}