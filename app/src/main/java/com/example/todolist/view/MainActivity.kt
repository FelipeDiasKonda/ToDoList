package com.example.todolist.view

import android.annotation.SuppressLint
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

    private lateinit var binding: ActivityMainBinding
    lateinit var database: ActivityDatabase
    private lateinit var activityViewModel: ActivityViewModel
    private lateinit var adapter: ActivityAdapter
    private lateinit var loadingDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val application = requireNotNull(this).application
        val factory = ActivityViewModelFactory(application)
        activityViewModel = ViewModelProvider(this,factory).get(ActivityViewModel::class.java)

        initRecyclerView()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private fun initRecyclerView() {

        //activityViewModel.deleteAllActivities()
        adapter = ActivityAdapter(activityViewModel){
            activity ->
            showDeleteDialog(activity)
        }
        binding.tasks.layoutManager = LinearLayoutManager(this)
        binding.tasks.adapter = adapter

        loadingDialog = Dialog(this)
        loadingDialog.setContentView(R.layout.loading_page)
        loadingDialog.setCancelable(false)
        loadingDialog.show()
        Handler(Looper.getMainLooper()).postDelayed({
            loadingDialog.dismiss()
        },3000)

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

    @SuppressLint("InflateParams")
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