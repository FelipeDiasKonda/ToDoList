package com.example.todolist.view

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.databinding.DialogDeleteActivityBinding
import com.example.todolist.model.ActivityModel
import com.example.todolist.viewmodel.ActivityViewModel
import com.example.todolist.model.ActivityAdapter
import com.example.todolist.model.ActivityDatabase
import com.example.todolist.viewmodel.ActivityViewModelFactory

class MainActivity : AppCompatActivity() {

    val database: ActivityDatabase by lazy{
        ActivityDatabase.getDatabase(applicationContext)
    }
    private val activityViewModel: ActivityViewModel by lazy {
        val application = requireNotNull(this).application
        val factory = ActivityViewModelFactory(application)
        ViewModelProvider(this, factory).get(ActivityViewModel::class.java)
    }
    private val adapter: ActivityAdapter by lazy{
        ActivityAdapter(activityViewModel){
                activity ->
            showDeleteDialog(activity)
        }
    }
    private val loadingDialog: Dialog by lazy {
        Dialog(this).apply {
            setContentView(R.layout.loading_page)
            setCancelable(false)
            show()
            Handler(Looper.getMainLooper()).postDelayed({
                dismiss()
            }, 3000)
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

        //activityViewModel.deleteAllActivities()
        binding.tasks.layoutManager = LinearLayoutManager(this)
        binding.tasks.adapter = adapter
        loadingDialog
        binding.floatingActionButton.setOnClickListener {
            val dialog = AddTaskActivity()
            dialog.show(supportFragmentManager,"AddActivity")

        }
        activityViewModel.allActivities.observe(this, Observer { activities ->
            if(activities.isNullOrEmpty()){
                binding.emptyMessage.visibility = View.VISIBLE
                binding.tasks.visibility = View.GONE
            } else {
                binding.emptyMessage.visibility = View.GONE
                binding.tasks.visibility = View.VISIBLE
                adapter.submitList(activities)
            }
        })
    }

    private fun showDeleteDialog(activity: ActivityModel){
        val dialogBinding = DialogDeleteActivityBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(true)

        dialogBinding.deleteButton.setOnClickListener{
            activityViewModel.deleteActivity(activity)
            dialog.dismiss()
        }
        dialog.show()
    }


}