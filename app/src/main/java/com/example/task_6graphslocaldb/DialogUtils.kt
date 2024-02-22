package com.example.task_6graphslocaldb

import MainViewModel
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.widget.EditText
import com.example.task_6graphslocaldb.roomDb.NameEntity

class DialogUtils(private val context: Context) {
    private val editName = context.getString(R.string.edit_name)
    private val update = context.getString(R.string.update)
    private val cancel = context.getString(R.string.cancel)
    private val delete = context.getString(R.string.delete)
    private val edit = context.getString(R.string.edit)

    fun showOptionsDialog(nameEntity: NameEntity, onEdit: () -> Unit, onDelete: () -> Unit) {
        val options = arrayOf(edit, delete)
        AlertDialog.Builder(context)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> onEdit.invoke()
                    1 -> onDelete.invoke()
                }
            }
            .show()
    }
    fun showEditDialog(nameEntity: NameEntity, onUpdate: (String) -> Unit) {
        val editText = EditText(context)
        editText.setText(nameEntity.name)
        AlertDialog.Builder(context)
            .setTitle(editName)
            .setView(editText)
            .setPositiveButton(update) { _, _ ->
                val newName = editText.text.toString()
                onUpdate.invoke(newName)
            }
            .setNegativeButton(cancel, null)
            .show()
    }
}