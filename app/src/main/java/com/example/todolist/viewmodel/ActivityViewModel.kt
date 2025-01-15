package com.example.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.model.ActivityDatabase
import com.example.todolist.model.ActivityModel
import com.example.todolist.model.ActivityRepository
import kotlinx.coroutines.launch

class ActivityViewModel(application: Application) : AndroidViewModel(application){

    private val repository: ActivityRepository
    val allActivities : LiveData<List<ActivityModel>>

    init{
        val dao = ActivityDatabase.getDatabase(application).activityDao()
        repository = ActivityRepository(dao)
        allActivities = repository.readAlLData()
    }
    fun addActivity(activity: ActivityModel){
        viewModelScope.launch{
            repository.addActivity(activity)
        }
    }

    fun completeActivity(activity: ActivityModel){
        activity.Done = true
        viewModelScope.launch{
            repository.updateActivity(activity)
        }
    }


    fun undoActivity(activity: ActivityModel){
        activity.Done = false
        viewModelScope.launch{
            repository.updateActivity(activity)
        }
    }

    fun deleteActivity(activity: ActivityModel){
        viewModelScope.launch{
            repository.deleteActivity(activity)
        }
    }

    fun deleteAllActivities(){
        viewModelScope.launch {
            repository.deleteAllActivities()
        }
    }



}