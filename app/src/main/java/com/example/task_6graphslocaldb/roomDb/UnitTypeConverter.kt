package com.example.task_6graphslocaldb.roomDb

import androidx.room.TypeConverter

class UnitTypeConverter {
    @TypeConverter
    fun fromUnit(unit: Unit?): String? {
        return null
    }

    @TypeConverter
    fun toUnit(unitString: String?): Unit? {
        return Unit
    }
}