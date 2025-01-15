package com.example.todolist.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ActivityModel::class], version = 1, exportSchema = false)
abstract class ActivityDatabase: RoomDatabase() {
    abstract fun activityDao(): ActivityDao

    companion object{
        @Volatile
        private var INSTANCE: ActivityDatabase? = null

        fun getDatabase(context: Context): ActivityDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return  tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ActivityDatabase::class.java,
                    "activity_database"
                ).build()
            INSTANCE = instance
            return instance
            }

        }
    }
}