package com.example.todolist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todolist.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.todolist.databinding.DialogAddActivityBinding
import com.example.todolist.model.ActivityModel
import com.example.todolist.viewmodel.ActivityViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class AddTaskActivity: BottomSheetDialogFragment() {

    private lateinit var _binding: DialogAddActivityBinding
    private val binding get() = _binding
    private lateinit var activityViewModel: ActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addActivityBtn.setOnClickListener {
            val id: UUID = UUID.randomUUID()
            val title = binding.AddTittle2.text.toString()
            val description = binding.AddDesc2.text.toString()
            val createDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
                Date()
            )
            val newActivity = ActivityModel(id,title,description,createDate)
            activityViewModel.addActivity(newActivity)
            dismiss()
        }
    }

}