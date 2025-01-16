package com.example.todolist.view
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.databinding.DialogDeleteActivityBinding
import com.example.todolist.model.TaskModel
import com.example.todolist.viewmodel.TaskViewModel
import com.example.todolist.model.TaskAdapter
import com.example.todolist.viewmodel.TaskViewModelFactory

class MainActivity : AppCompatActivity() {
    private val taskViewModel: TaskViewModel by lazy {
        val application = requireNotNull(this).application
        val factory = TaskViewModelFactory(application)
        ViewModelProvider(this, factory)[TaskViewModel::class.java]
    }
    private val adapter: TaskAdapter by lazy{
        TaskAdapter(taskViewModel){
                activity ->
            showDeleteDialog(activity)
        }
    }
    private val loadingDialog: Dialog by lazy {
        Dialog(this).apply {
            setContentView(R.layout.loading_page)
            setCancelable(false)
            show()
            val progressBar = findViewById<ProgressBar>(R.id.progressBar)
            progressBar.visibility = View.VISIBLE
            Handler(Looper.getMainLooper()).postDelayed({
                progressBar.progress = 50
                progressBar.visibility = View.GONE
                dismiss()
            }, 2000)
        }
    }
    private val binding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        initSetup()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
    private fun initSetup() {
        binding.tasks.layoutManager = LinearLayoutManager(this)
        binding.tasks.adapter = adapter
        loadingDialog
        binding.floatingActionButton.setOnClickListener {
            binding.floatingActionButton.isEnabled = false
            val dialog = AddTaskFragment()
            dialog.show(supportFragmentManager,"AddActivity")
            Handler(Looper.getMainLooper()).postDelayed({
                binding.floatingActionButton.isEnabled = true
            }, 1000)
        }
        taskViewModel.allActivities.observe(this) { activities ->
            if (activities.isNullOrEmpty()) {
                binding.emptyMessage.visibility = View.VISIBLE
                binding.tasks.visibility = View.GONE
            } else {
                binding.emptyMessage.visibility = View.GONE
                binding.tasks.visibility = View.VISIBLE
                adapter.submitList(activities)
            }
        }
    }
    private fun showDeleteDialog(activity: TaskModel){
        val dialogBinding = DialogDeleteActivityBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(true)

        dialogBinding.deleteButton.setOnClickListener{
            taskViewModel.deleteActivity(activity)
            dialog.dismiss()
        }
        dialog.show()
    }
}