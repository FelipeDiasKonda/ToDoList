package com.example.todolist.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ViewTaskLayoutBinding
import com.example.todolist.viewmodel.TaskViewModel

class TaskAdapter(
    private val taskViewModel: TaskViewModel,
    private val onItemLongClickListener: (TaskModel) -> Unit
) : ListAdapter<TaskModel, TaskAdapter.ActivityViewHolder>(TaskDiffCallBack()) {

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
        fun bind(activity: TaskModel) {
            binding.titleTxt.text = activity.title
            binding.descTxt.text = activity.description
            binding.Createdate.text = activity.created_date
            binding.checkBox.setOnCheckedChangeListener(null)
            binding.checkBox.isChecked = activity.done

            binding.root.setOnClickListener {
                binding.checkBox.isChecked = !binding.checkBox.isChecked
            }

            binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    taskViewModel.completeActivity(activity)
                } else {
                    taskViewModel.undoActivity(activity)
                }
            }


        }
    }
}