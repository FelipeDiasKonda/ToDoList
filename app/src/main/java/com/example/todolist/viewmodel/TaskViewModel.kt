package com.example.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.model.TaskDatabase
import com.example.todolist.model.TaskModel
import com.example.todolist.model.TaskRepository
import kotlinx.coroutines.launch


class TaskViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val repository: TaskRepository
    val allTasks: LiveData<List<TaskModel>>

    init {
        val dao = TaskDatabase(application).activityDao()
        repository = TaskRepository(dao)
        allTasks = repository.readAlLData()
    }


    fun completeTask(activity: TaskModel) {
        activity.done = true
        viewModelScope.launch {
            repository.updateTask(activity)
        }
    }


    fun undoTask(task: TaskModel) {
        task.done = false
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun deleteTask(task: TaskModel) {
        viewModelScope.launch {

            repository.deleteTask(task)

        }
    }


}