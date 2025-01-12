package com.example.todolist.view

import android.os.Build.VERSION_CODES.S
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.todolist.databinding.AddActivityBinding
import com.example.todolist.model.ActivityModel
import com.example.todolist.viewmodel.ActivityViewModel
import com.example.todolist.viewmodel.ActivityViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class AddTaskActivity: BottomSheetDialogFragment() {

    private lateinit var _binding: AddActivityBinding
    private val binding get() = _binding
    private lateinit var activityViewModel: ActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val factory = ActivityViewModelFactory(application)
        activityViewModel = ViewModelProvider(this,factory).get(ActivityViewModel::class.java)

        binding.addActivityBtn.setOnClickListener {
            val id: UUID = UUID.randomUUID()
            val title = binding.AddTittle2.text.toString()
            val description = binding.AddDesc2.text.toString()
            val createDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
                Date()

            )
            val newActivity = ActivityModel(id,title,description,createDate,false)
            activityViewModel.addActivity(newActivity)
            dismiss()
        }
    }

}