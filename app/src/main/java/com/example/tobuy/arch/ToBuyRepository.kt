package com.example.tobuy.arch

import com.example.tobuy.database.db.AppDatabase
import com.example.tobuy.model.DataItem
import kotlinx.coroutines.flow.Flow

class ToBuyRepository(
    private val appDatabase: AppDatabase
) {

    suspend fun insertItem(itemEntity: DataItem.ItemEntity) {
        appDatabase.itemEntityDao().insert(itemEntity)
    }

    suspend fun deleteItem(itemEntity: DataItem.ItemEntity) {
        appDatabase.itemEntityDao().delete(itemEntity)
    }

    suspend fun updateItem(itemEntity: DataItem.ItemEntity) {
        appDatabase.itemEntityDao().update(itemEntity)
    }

    fun getAllItems(): Flow<List<DataItem.ItemEntity>> {
        return appDatabase.itemEntityDao().getAllItemEntities()
    }
}