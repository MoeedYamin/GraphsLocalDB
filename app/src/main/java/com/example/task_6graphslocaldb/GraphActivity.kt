package com.example.task_6graphslocaldb

import MainViewModel
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.task_6graphslocaldb.databinding.ActivityGraphBinding
import com.example.task_6graphslocaldb.roomDb.NameEntity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GraphActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGraphBinding
    private lateinit var barChart: BarChart
    private lateinit var viewModel: MainViewModel
    lateinit var toolBar: Toolbar
    private var nameEntity: NameEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializingBinding()
        initializingToolBar()
        initializingViewModel()
        initializingBarChart()
        initializingListName()
    }

    private fun initializingViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    private fun initializingListName() {
        val listNameTextView = binding.listNameTextView
        val listId = intent.getLongExtra(ProjectConstants.GRAPH_NAME_ENTITY_ID, -1)
        if (listId != (-1).toLong()) {
            CoroutineScope(Dispatchers.IO).launch {
                nameEntity = viewModel.getNameEntityById(listId)
                withContext(Dispatchers.Main) {
                    listNameTextView.text = nameEntity?.name ?: ""
                }
            }
        }
    }


    private fun initializingBarChart() {
        barChart = binding.barChart
        val listId = intent.getLongExtra(ProjectConstants.GRAPH_NAME_ENTITY_ID, -1)
        if (listId != (-1).toLong()) {
            CoroutineScope(Dispatchers.IO).launch {
                nameEntity = viewModel.getNameEntityById(listId)
                if (nameEntity != null) {
                    val values = viewModel.getValuesForList(listId)
                    withContext(Dispatchers.Main) {
                        val values = viewModel.getValuesForList(listId)
                        val entries = mutableListOf<BarEntry>()
                        values.forEachIndexed { index, value ->
                            entries.add(BarEntry(index.toFloat(), value.value.toFloat()))
                        }
                        val dataSet = BarDataSet(entries, getString(R.string.bar_chart))
                        val dataSets = mutableListOf<IBarDataSet>()
                        dataSets.add(dataSet)
                        dataSet.setColors(R.color.light_purple)
                        val data = BarData(dataSets)
                        barChart.data = data
                        barChart.invalidate()
                    }
                }
            }
        }
    }

    private fun initializingBinding() {
        binding = ActivityGraphBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun initializingToolBar() {
        toolBar = findViewById(R.id.toolbar)
        setSupportActionBar(toolBar)
        supportActionBar?.title = getString(R.string.graph_activity)

    }
}
