package com.example.task_6graphslocaldb.roomDb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface NameDao {
    @Query("SELECT * FROM name_table ORDER BY id DESC")
    fun getAllNames(): LiveData<List<NameEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertName(name: NameEntity)

    @Update
    suspend fun updateName(name: NameEntity)

    @Delete
    suspend fun deleteName(name: NameEntity)

    @Query("SELECT * FROM name_table WHERE id = :id")
    suspend fun getNameById(id: Long): NameEntity?

}

@Dao
interface ValueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertValue(valueEntity: ValueEntity)

    @Query("DELETE FROM value_table WHERE listId = :listId")
    suspend fun deleteValuesByListId(listId: Long)

    @Query("SELECT * FROM value_table WHERE listId = :listId")
    suspend fun getValuesForList(listId: Long): List<ValueEntity>
}