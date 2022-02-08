package com.example.tobuy.database.dao

import androidx.room.*
import com.example.tobuy.model.DataItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemEntityDao {

    @Query("SELECT * FROM item_entity")
    fun getAllItemEntities(): Flow<List<DataItem.ItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(itemEntity: DataItem.ItemEntity)

    @Delete
    suspend fun delete(itemEntity: DataItem.ItemEntity)

    @Update
    suspend fun update(itemEntity: DataItem.ItemEntity)
}