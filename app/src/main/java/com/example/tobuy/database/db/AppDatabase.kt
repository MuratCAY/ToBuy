package com.example.tobuy.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tobuy.database.dao.CategoryEntityDao
import com.example.tobuy.database.dao.ItemEntityDao
import com.example.tobuy.database.migration.MIGRATION_1_2
import com.example.tobuy.model.DataItem

@Database(
    entities = [DataItem.ItemEntity::class, DataItem.CategoryEntity::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun itemEntityDao(): ItemEntityDao
    abstract fun categoryEntityDao(): CategoryEntityDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "to_buy_database"
                ).addMigrations(MIGRATION_1_2())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}