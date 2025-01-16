package com.example.todolist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.todolist.databinding.AddActivityBinding
import com.example.todolist.model.TaskModel
import com.example.todolist.viewmodel.TaskViewModel
import com.example.todolist.viewmodel.TaskViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class AddTaskFragment: BottomSheetDialogFragment() {

    private val binding: AddActivityBinding by lazy {
        AddActivityBinding.inflate(layoutInflater)
    }

    private val taskViewModel: TaskViewModel by lazy {
        val application = requireNotNull(this.activity).application
        val factory = TaskViewModelFactory(application)
        ViewModelProvider(this, factory)[TaskViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addActivityBtn.setOnClickListener {
            val id: UUID = UUID.randomUUID()
            val title = binding.AddTittle2.text.toString()
            val description = binding.AddDesc2.text.toString()
            val pattern = getLocalizedPattern(Locale.getDefault())
            val createDate = SimpleDateFormat(pattern, Locale.getDefault()).format(
                Date()
            )
            val newActivity = TaskModel(id, title, description, createDate, false)
            if (title.isBlank()) {
                binding.AddTitle.hint = "Titulo é obrigatório"
            } else {
                taskViewModel.addActivity(newActivity)
                dismiss()
            }
        }
        /*binding.addtask.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            binding.addtask.getWindowVisibleDisplayFrame(rect)
            val screenHeight = binding.addtask.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            if (keypadHeight > screenHeight * 0.15) {
                binding.addtask.setPadding(0, 0, 0, keypadHeight + 3)
            } else {
                binding.addtask.setPadding(0, 0, 0, 0)
            }
        }*/
    }

    private fun getLocalizedPattern(locale: Locale = Locale.getDefault()): String {
        return when (locale) {
            Locale.US -> "MM/dd/yyyy HH:mm:ss"
            Locale("pt", "BR") -> "dd/MM/yyyy HH:mm:ss"
            else -> "yyyy-MM-dd HH:mm:ss"
        }
    }
}