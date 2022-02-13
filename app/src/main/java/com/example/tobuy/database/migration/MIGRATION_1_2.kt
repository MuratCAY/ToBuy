package com.example.tobuy.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class MIGRATION_1_2 : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS 'category_entity' ('id' TEXT not null, 'name' TEXT not null, PRIMARY KEY ('id'))")
    }
}