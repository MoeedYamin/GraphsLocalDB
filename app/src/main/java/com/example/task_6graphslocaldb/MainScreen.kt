package com.example.task_6graphslocaldb

import MainViewModel
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task_6graphslocaldb.databinding.ActivityMainScreenBinding
import com.example.task_6graphslocaldb.roomDb.NameEntity
import com.example.task_6graphslocaldb.roomDb.NameListAdapter
class MainScreen : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainScreenBinding
    private lateinit var adapter: NameListAdapter
    private lateinit var manager: LinearLayoutManager
    private lateinit var dialogUtils: DialogUtils
    private var noData = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModelInitializer()
        dialogUtils = DialogUtils(this)
        recyclerViewInitializer()
        clickListeners()
        setItemLongClick()
        setObserver()

    }

    private fun setItemLongClick() {
        adapter.setOnItemLongClickListener { nameEntity ->
            showOptionsDialog(nameEntity)
        }
    }

    private fun showOptionsDialog(nameEntity: NameEntity) {
        dialogUtils.showOptionsDialog(nameEntity, { showEditDialog(nameEntity) }) {
            deleteNameAndValues(nameEntity)
        }
    }

    private fun showEditDialog(nameEntity: NameEntity) {
        dialogUtils.showEditDialog(nameEntity) { newName ->
            updateNameInDatabase(nameEntity, newName)
        }
    }


    private fun updateNameInDatabase(nameEntity: NameEntity, newName: String) {
        nameEntity.name = newName
        viewModel.updateName(nameEntity)
    }

    private fun deleteNameAndValues(nameEntity: NameEntity) {
        viewModel.deleteNameAndValues(nameEntity)
    }


    private fun clickListeners() {
        binding.addNewListButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, AddNewListActivity::class.java)
            startActivity(intent)
        })


    }

    private fun recyclerViewInitializer() {
        initViews()
        binding.nameRecyclerView.adapter = adapter
        binding.nameRecyclerView.layoutManager = manager

    }

    private fun setObserver()
    {
        viewModel.namesLiveData.observe(this) { names ->
            adapter.setNames(names)
            noData = names.isEmpty()
            binding.noDataFoundTextView.visibility =
                if (noData) View.VISIBLE else View.GONE
        }


    }

    private fun viewModelInitializer()
    {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    private fun initViews()
    {
        manager = LinearLayoutManager(this)
        adapter = NameListAdapter()
    }
}