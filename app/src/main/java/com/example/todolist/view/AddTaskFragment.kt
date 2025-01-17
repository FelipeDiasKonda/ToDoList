package com.example.todolist.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.todolist.databinding.AddActivityBinding
import com.example.todolist.model.TaskModel
import com.example.todolist.viewmodel.AddTaskViewModel
import com.example.todolist.viewmodel.TaskViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class AddTaskFragment : BottomSheetDialogFragment() {

    private val binding: AddActivityBinding by lazy {
        AddActivityBinding.inflate(layoutInflater)
    }

    private val addTaskViewModel: AddTaskViewModel by lazy {
        val application = requireNotNull(this.activity).application
        val factory = TaskViewModelFactory(application)
        ViewModelProvider(this, factory)[AddTaskViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.peekHeight = 0
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                it.layoutParams.height = (resources.displayMetrics.heightPixels * 0.4).toInt()
                behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                            dismiss()
                        }
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    }
                })
            }
        }
        return dialog
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
                binding.AddTitle.hint = getString(R.string.required)
            } else {
                addTaskViewModel.addActivity(newActivity)
                dismiss()
            }
        }
    }

    private fun getLocalizedPattern(locale: Locale = Locale.getDefault()): String {
        return when (locale) {
            Locale.US -> "MM/dd/yyyy HH:mm:ss"
            Locale("pt", "BR") -> "dd/MM/yyyy HH:mm:ss"
            else -> "yyyy-MM-dd HH:mm:ss"
        }
    }
}

