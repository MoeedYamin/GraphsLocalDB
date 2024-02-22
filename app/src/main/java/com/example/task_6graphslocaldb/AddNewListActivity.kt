package com.example.task_6graphslocaldb

import MainViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.task_6graphslocaldb.databinding.ActivityAddNewListBinding
import com.example.task_6graphslocaldb.roomDb.NameEntity
import com.example.task_6graphslocaldb.roomDb.ValueEntity

class AddNewListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewListBinding
    private lateinit var viewModel: MainViewModel
    private val dynamicEditTexts = arrayListOf<EditText>()
    private var switch = false
    lateinit var toolBar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingViews()
        viewModelInitializing()
        setObservers()
        initializingToolBar()
        clickListeners()
    }

    private fun setObservers() {
        viewModel.namesLiveData.observe(this) { nameEntities ->
            for (nameEntity in nameEntities) {
                Log.d("LiveDataUpdate", "Name: ${nameEntity.name}")
            }
        }
    }

    private fun initializingToolBar() {
        toolBar = findViewById(R.id.toolbar)
        setSupportActionBar(toolBar)
        supportActionBar?.title = getString(R.string.add_new_list)
    }

    private fun viewModelInitializing() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]


    }


    private fun clickListeners() {
        binding.addNewValueButton.setOnClickListener(View.OnClickListener {
            if (binding.addNewListEditText.text.isEmpty()) {
                binding.requireMessage.visibility = View.VISIBLE
            } else {
                addDynamicEditText()
            }
        })
        binding.doneButton.setOnClickListener(View.OnClickListener {
            if (binding.addNewListEditText.text.isEmpty()) {
                binding.requireMessage.visibility = View.VISIBLE
            } else {
                binding.requireMessage.visibility = View.INVISIBLE
                if (switch == false) {
                    Toast.makeText(
                        this,
                        getString(R.string.all_fields_are_required),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val allFieldsValid = validateDynamicEditTexts()
                    if (allFieldsValid) {
                        onDoneButtonClick()
                    } else {
                        Toast.makeText(this, getString(R.string.invalid_value), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })
    }

    private fun validateDynamicEditTexts(): Boolean {
        for (editText in dynamicEditTexts) {
            val text = editText.text.toString()
            if (text.isEmpty()) {
                return false
            } else if (!text.matches(Regex("-?\\d+"))) {
                return false
            }
        }
        return true
    }

    private fun addDynamicEditText() {
        switch = true
        if (dynamicEditTexts.size < 5) {
            val editText = createDynamicEditText()
            dynamicEditTexts.add(editText)
            binding.valuesContainer.addView(editText)
        } else {
            Toast.makeText(this, getString(R.string.you_can_add_upto_5_values), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun createDynamicEditText(): EditText {
        val editText = EditText(this)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
        editText.layoutParams = layoutParams
        editText.hint = getString(R.string.value)
        editText.inputType = InputType.TYPE_CLASS_NUMBER
        return editText
    }

    private fun onDoneButtonClick() {
        val name = binding.addNewListEditText.text.toString()
        val values = dynamicEditTexts.map { it.text.toString() }
        if (name.isEmpty()) {
            Toast.makeText(this, getString(R.string.list_name_is_required), Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (dynamicEditTexts.size < 5) {
            Toast.makeText(this, getString(R.string.all_fields_are_required), Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (values.any { it.isEmpty() }) {
            Toast.makeText(this, getString(R.string.all_fields_are_required), Toast.LENGTH_SHORT)
                .show()
            return
        } else {
            val nameEntity = NameEntity(name = name)
            viewModel.insertName(nameEntity)

            viewModel.namesLiveData.observe(this) { names ->
                val insertedNameEntity = names.find { it.name == name }
                if (insertedNameEntity != null) {
                    val insertedNameId = insertedNameEntity.id
                    values.forEach { value ->
                        Log.d(
                            "ValueInsertion",
                            "Inserting value: $value for list ID: $insertedNameId"
                        )
                        val valueEntity = ValueEntity(listId = insertedNameId, value = value)
                        viewModel.insertValue(valueEntity)
                    }
                    finish()
                }
            }
        }
    }

    private fun bindingViews() {
        binding = ActivityAddNewListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }


}