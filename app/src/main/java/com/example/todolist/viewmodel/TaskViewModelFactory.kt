package com.example.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TaskViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TaskViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                TaskViewModel(application) as T
            }
            modelClass.isAssignableFrom(AddTaskViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                AddTaskViewModel(application) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}