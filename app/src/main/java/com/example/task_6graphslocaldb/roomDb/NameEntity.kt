package com.example.task_6graphslocaldb.roomDb

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "name_table")
data class NameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var name: String
)
@Entity(
    tableName = "value_table",
    foreignKeys = [ForeignKey(
        entity = NameEntity::class,
        parentColumns = ["id"],
        childColumns = ["listId"],
        onDelete = ForeignKey.CASCADE
    )]
)
@TypeConverters(UnitTypeConverter::class)
data class ValueEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val listId: Long,
    val value: String
    )