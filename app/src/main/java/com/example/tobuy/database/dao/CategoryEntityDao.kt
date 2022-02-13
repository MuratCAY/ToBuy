package com.example.tobuy.database.dao

import androidx.room.*
import com.example.tobuy.model.DataItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryEntityDao {

    @Query("SELECT * FROM category_entity")
    fun getAllItemEntities(): Flow<List<DataItem.CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categoryEntity: DataItem.CategoryEntity)

    @Delete
    suspend fun delete(categoryEntity: DataItem.CategoryEntity)

    @Update
    suspend fun update(categoryEntity: DataItem.CategoryEntity)
}