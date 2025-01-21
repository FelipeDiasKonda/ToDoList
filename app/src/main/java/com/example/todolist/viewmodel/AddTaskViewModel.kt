package com.example.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.model.TaskDatabase
import com.example.todolist.model.TaskModel
import com.example.todolist.model.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddTaskViewModel(application: Application): AndroidViewModel(application) {
    private val repository: TaskRepository
    private val defaultDispatcher = Dispatchers.IO
    init{
        val dao = TaskDatabase.getDatabase(application).activityDao()
        repository = TaskRepository(dao)
    }
    fun addActivity(activity: TaskModel) {
        viewModelScope.launch {
            withContext(defaultDispatcher) {
                repository.addActivity(activity)
            }
        }
    }
}