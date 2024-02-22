package com.example.task_6graphslocaldb.roomDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.task_6graphslocaldb.ProjectConstants

@Database(entities = [NameEntity::class, ValueEntity::class], version = 2, exportSchema = false)
abstract class MyAppDatabase : RoomDatabase() {
    abstract fun nameDao(): NameDao
    abstract fun valueDao(): ValueDao

    companion object {
        @Volatile
        private var INSTANCE: MyAppDatabase? = null
        fun getDatabase(context: Context): MyAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyAppDatabase::class.java,
                    ProjectConstants.DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}