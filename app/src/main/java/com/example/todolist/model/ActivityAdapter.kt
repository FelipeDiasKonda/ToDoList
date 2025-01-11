package com.example.todolist.model

import ActivityDiffCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ViewTaskLayoutBinding

class ActivityAdapter : ListAdapter<ActivityModel, ActivityAdapter.ActivityViewHolder>(ActivityDiffCallback()) {

    private val activityList = ArrayList<ActivityModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val binding = ViewTaskLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActivityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val activity = getItem(position)
        holder.bind(activity)
    }

    inner class ActivityViewHolder(private val binding: ViewTaskLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(activity: ActivityModel) {
            binding.titleTxt.text = activity.Title
            binding.descTxt.text = activity.Description
            binding.Createdate.text = activity.CreatedDate
            binding.checkBox.isChecked = activity.Done
        }
    }
    fun updateList(newList: List<ActivityModel>){
        activityList.clear()
        activityList.addAll(newList)
    }
}