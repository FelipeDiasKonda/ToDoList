package com.example.todolist.model

import ActivityDiffCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ViewTaskLayoutBinding
import com.example.todolist.viewmodel.ActivityViewModel

class ActivityAdapter(
    private val activityViewModel: ActivityViewModel,
    private val onItemLongClickListener: (ActivityModel) -> Unit
) : ListAdapter<ActivityModel, ActivityAdapter.ActivityViewHolder>(ActivityDiffCallback()) {

    private val activityList = ArrayList<ActivityModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val binding = ViewTaskLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActivityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
        holder.itemView.setOnLongClickListener{
            onItemLongClickListener(currentItem)
            true
        }
        val activity = getItem(position)
        holder.bind(activity)
    }

    inner class ActivityViewHolder(private val binding: ViewTaskLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(activity: ActivityModel) {
            binding.titleTxt.text = activity.Title
            binding.descTxt.text = activity.Description
            binding.Createdate.text = activity.CreatedDate
            binding.checkBox.setOnCheckedChangeListener(null)
            binding.checkBox.isChecked = activity.Done

            binding.root.setOnClickListener {
                binding.checkBox.isChecked = !binding.checkBox.isChecked
            }


            binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    activityViewModel.completeActivity(activity)
                } else {
                    activityViewModel.undoActivity(activity)
                }
            }


        }
    }
}