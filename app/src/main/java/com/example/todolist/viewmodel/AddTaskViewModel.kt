package com.example.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.model.TaskDatabase
import com.example.todolist.model.TaskModel
import com.example.todolist.model.TaskRepository
import kotlinx.coroutines.launch


class AddTaskViewModel(
    application: Application,
) : AndroidViewModel(application) {
    private val repository: TaskRepository

    init {
        val dao = TaskDatabase(application).activityDao()
        repository = TaskRepository(dao)
    }

    fun addTask(activity: TaskModel) {
        viewModelScope.launch {
            repository.addTask(activity)
        }
    }
}