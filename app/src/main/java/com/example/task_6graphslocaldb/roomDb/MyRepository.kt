package com.example.task_6graphslocaldb.roomDb

import android.util.Log
import androidx.lifecycle.LiveData

class MyRepository(private val nameDao: NameDao, private val valueDao: ValueDao) {

    val namesLiveData: LiveData<List<NameEntity>> = nameDao.getAllNames()
    suspend fun insertName(name: NameEntity) {
        nameDao.insertName(name)
        Log.d("DataInsert", "NameEntity inserted: ${name.name}")
    }
    suspend fun insertValue(valueEntity: ValueEntity) {
        valueDao.insertValue(valueEntity)

    }
    suspend fun updateName(nameEntity: NameEntity) {
        nameDao.updateName(nameEntity)
    }
    suspend fun deleteNameAndValues(nameEntity: NameEntity) {
        nameDao.deleteName(nameEntity)
        valueDao.deleteValuesByListId(nameEntity.id)
    }

    suspend fun getNameEntityById(id: Long): NameEntity? {
        return nameDao.getNameById(id)
    }


    suspend fun getValuesForList(listId: Long): List<ValueEntity> {
        val values = valueDao.getValuesForList(listId)
        Log.d("DataRetrieval", "Values retrieved: $values") // Add this log statement
        return values
    }
}